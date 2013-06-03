package grytsenko.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Processes a requests for logging.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoginController.class);

    /**
     * User tries to login.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * User tries to login after error.
     */
    @RequestMapping(params = "retry", method = RequestMethod.GET)
    public String loginFailed(Model model) {
        LOGGER.debug("Login failed.");

        model.addAttribute("lastLoginFailed", true);

        return "login";
    }

}
