package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.User;
import grytsenko.library.service.SearchService;
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
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Provides access to user details.
 */
@Controller
@RequestMapping(value = "/user")
@SessionAttributes({ "user" })
public class UserController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserController.class);

    private UserService userService;
    private SearchService searchService;

    @Autowired
    public UserController(UserService userService, SearchService searchService) {
        this.userService = userService;
        this.searchService = searchService;
    }

    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUser(@ModelAttribute("user") User user, Model model) {
        LOGGER.debug("Get user details.");

        List<Book> relatedBooks = searchService.findRelatedTo(user);
        LOGGER.debug("{} books are related to {}.", relatedBooks.size(),
                user.getUsername());
        if (!relatedBooks.isEmpty()) {
            model.addAttribute("relatedBooks", relatedBooks);
        }

        return "user";
    }

}
