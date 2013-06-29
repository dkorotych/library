package grytsenko.library.view.controller;

import static grytsenko.library.view.MappingConstants.SEARCH_PATH;

import java.text.MessageFormat;

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
        return MessageFormat.format("redirect:{0}", SEARCH_PATH);
    }

}
