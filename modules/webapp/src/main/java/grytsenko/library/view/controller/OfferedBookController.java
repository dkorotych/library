package grytsenko.library.view.controller;

import static grytsenko.library.view.MappingConstants.BOOK_ID_PARAM;
import static grytsenko.library.view.MappingConstants.OFFERED_BOOK_PATH;
import static grytsenko.library.view.MappingConstants.USER_ATTR;
import grytsenko.library.model.OfferedBook;
import grytsenko.library.model.User;
import grytsenko.library.service.BookNotUpdatedException;
import grytsenko.library.service.ManageOfferedBooksService;
import grytsenko.library.service.ManageUsersService;
import grytsenko.library.service.SearchOfferedBooksService;

import java.security.Principal;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Processes requests for offered book.
 */
@Controller
@RequestMapping(value = OFFERED_BOOK_PATH, params = BOOK_ID_PARAM)
@SessionAttributes({ "user" })
public class OfferedBookController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OfferedBookController.class);

    @Autowired
    ManageUsersService manageUsersService;

    @Autowired
    SearchOfferedBooksService searchOfferedBooksService;
    @Autowired
    ManageOfferedBooksService manageOfferedBooksService;

    @ModelAttribute(USER_ATTR)
    public User currentUser(Principal principal) {
        return manageUsersService.find(principal.getName());
    }

    /**
     * User views details about book.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getBook(@RequestParam(BOOK_ID_PARAM) Long bookId, Model model) {
        LOGGER.debug("Find book {}.", bookId);

        OfferedBook book = searchOfferedBooksService.find(bookId);
        model.addAttribute("book", book);

        LOGGER.debug("Book has {} votes.", book.getVotesNum());
        return OFFERED_BOOK_PATH;
    }

    /**
     * User votes for book.
     */
    @RequestMapping(params = "addVote", method = RequestMethod.POST)
    public String addVote(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws BookNotUpdatedException {
        LOGGER.debug("{} votes for book {}.", user.getUsername(), bookId);

        OfferedBook book = searchOfferedBooksService.find(bookId);
        manageOfferedBooksService.addVote(book, user);

        return redirectToBook(bookId);
    }

    /**
     * If book was not updated, then notification should be shown.
     */
    @ExceptionHandler(BookNotUpdatedException.class)
    public String whenBookNotUpdated(BookNotUpdatedException exception,
            HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter(BOOK_ID_PARAM));
        LOGGER.warn("Book {} was not updated, because: '{}'.", bookId,
                exception.getMessage());

        FlashMap attrs = RequestContextUtils.getOutputFlashMap(request);
        attrs.put("bookNotUpdated", true);

        return redirectToBook(bookId);
    }

    private static String redirectToBook(Long bookId) {
        return MessageFormat.format("redirect:{0}?bookId={1}",
                OFFERED_BOOK_PATH, bookId);
    }

}
