package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.SearchResults;
import grytsenko.library.model.User;
import grytsenko.library.service.ManageUsersService;
import grytsenko.library.service.SearchBooksService;

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
 * Processes a search requests.
 */
@Controller
@RequestMapping("/search")
@SessionAttributes({ "user" })
public class SearchController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchController.class);

    public static final int PAGE_SIZE = 3;

    @Autowired
    ManageUsersService manageUsersService;
    @Autowired
    SearchBooksService searchBooksService;

    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return manageUsersService.find(principal.getName());
    }

    /**
     * User views list of books.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String searchAll(
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @ModelAttribute("user") User user, Model model) {
        LOGGER.debug("Get page {}.", pageNum);

        SearchResults<Book> books = searchBooksService.findAll(pageNum,
                PAGE_SIZE);
        model.addAttribute("searchResults", books);

        return "search";
    }

}
