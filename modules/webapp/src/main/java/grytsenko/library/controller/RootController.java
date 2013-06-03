package grytsenko.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Processes a request to root domain.
 */
@Controller
@RequestMapping("/")
public class RootController {

    /**
     * Processes a request to root domain.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String root() {
        return "redirect:/books";
    }

}
