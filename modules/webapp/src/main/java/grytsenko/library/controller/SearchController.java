package grytsenko.library.controller;

import static java.util.Collections.emptyList;
import grytsenko.library.model.Book;
import grytsenko.library.model.BookFilter;
import grytsenko.library.model.User;
import grytsenko.library.service.BookService;
import grytsenko.library.service.BookServiceException;
import grytsenko.library.service.UserService;

import java.security.Principal;
import java.util.List;

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
 * Manages a requests for books in library.
 */
@Controller
@RequestMapping("/search")
@SessionAttributes({ "user", "filter" })
public class SearchController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchController.class);

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
     * Adds default filter.
     */
    @ModelAttribute("filter")
    public BookFilter addDefaultFilter() {
        BookFilter filter = BookFilter.getDefault();
        LOGGER.debug("Current filter is {}.", filter);
        return filter;
    }

    /**
     * User views list of books.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(Model model, @ModelAttribute("user") User user,
            @ModelAttribute("filter") BookFilter filter) {
        LOGGER.debug("Get {} books.", filter);

        try {
            List<Book> books = bookService.find(filter, user);
            LOGGER.debug("Found {} book(s).", books.size());
            model.addAttribute("books", books);
        } catch (BookServiceException exception) {
            LOGGER.warn("Books are not accessible.");
            model.addAttribute("books", emptyList());
        }

        return "search";
    }

    /**
     * User applies filter to list of books.
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(Model model,
            @RequestParam("selectedFilter") String selectedFilter) {
        LOGGER.debug("User selects filter {}.", selectedFilter);

        BookFilter filter = BookFilter.fromString(selectedFilter);

        model.addAttribute("filter", filter);
        LOGGER.debug("Current filter is {}.", filter);

        return "redirect:/search";
    }

}
