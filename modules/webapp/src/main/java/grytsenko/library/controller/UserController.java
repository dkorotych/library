package grytsenko.library.controller;

import grytsenko.library.model.User;
import grytsenko.library.service.UserService;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUser() {
        LOGGER.debug("Get user details.");
        return "user";
    }

}
