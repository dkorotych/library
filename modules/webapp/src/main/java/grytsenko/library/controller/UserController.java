package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.User;
import grytsenko.library.service.BookService;
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
    private BookService bookService;

    @Autowired
    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUser(@ModelAttribute("user") User user, Model model) {
        LOGGER.debug("Get user details.");

        List<Book> reservedBooks = bookService.findReservedBy(user);
        LOGGER.debug("User has reserved {} books.", reservedBooks.size());
        if (!reservedBooks.isEmpty()) {
            model.addAttribute("reservedBooks", reservedBooks);
        }

        List<Book> borrowedBook = bookService.findBorrowedBy(user);
        LOGGER.debug("User has borrowed {} books.", borrowedBook.size());
        if (!borrowedBook.isEmpty()) {
            model.addAttribute("borrowedBooks", borrowedBook);
        }

        return "user";
    }

}
