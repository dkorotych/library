package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.SearchResults;
import grytsenko.library.model.User;
import grytsenko.library.service.BookService;
import grytsenko.library.service.BookServiceException;
import grytsenko.library.service.UserService;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Manages requests for search of books in library.
 */
@Controller
@RequestMapping("/search")
@SessionAttributes({ "user" })
public class SearchController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchController.class);

    /**
     * The default page size.
     */
    public static final int PAGE_SIZE = 3;

    private UserService userService;
    private BookService bookService;

    /**
     * Creates and initializes a controller.
     */
    @Autowired
    public SearchController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    /**
     * Adds current user.
     */
    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    /**
     * User views list of books.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ModelAttribute("user") User user, Model model) {
        if (pageNum == null || pageNum < 0) {
            pageNum = 0;
        }

        LOGGER.debug("Get page {}.", pageNum);

        try {
            SearchResults<Book> books = bookService.find(pageNum, PAGE_SIZE);
            model.addAttribute("searchResults", books);
        } catch (BookServiceException exception) {
            LOGGER.warn("Search failed.");
        }

        return "search";
    }

}
