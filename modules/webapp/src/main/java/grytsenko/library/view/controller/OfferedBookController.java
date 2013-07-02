package grytsenko.library.view.controller;

import static grytsenko.library.view.Navigation.BOOK_ID_PARAM;
import static grytsenko.library.view.Navigation.CURRENT_USER_ATTR;
import static grytsenko.library.view.Navigation.OFFERED_BOOK_PATH;
import static grytsenko.library.view.Navigation.getBookIdFromRequest;
import static grytsenko.library.view.Navigation.redirectToOfferedBook;
import static grytsenko.library.view.Navigation.redirectToSharedBook;
import static grytsenko.library.view.Navigation.redirectToVote;
import grytsenko.library.model.book.OfferedBook;
import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;
import grytsenko.library.service.book.BookNotFoundException;
import grytsenko.library.service.book.BookNotUpdatedException;
import grytsenko.library.service.book.ManageOfferedBooksService;
import grytsenko.library.service.book.SearchOfferedBooksService;
import grytsenko.library.service.user.ManageUsersService;
import grytsenko.library.service.user.NotifyUsersService;

import java.security.Principal;

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
@SessionAttributes(CURRENT_USER_ATTR)
public class OfferedBookController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(OfferedBookController.class);

    @Autowired
    ManageUsersService manageUsersService;
    @Autowired
    NotifyUsersService notifyUsersService;

    @Autowired
    SearchOfferedBooksService searchOfferedBooksService;
    @Autowired
    ManageOfferedBooksService manageOfferedBooksService;

    @ModelAttribute(CURRENT_USER_ATTR)
    public User currentUser(Principal principal) {
        return manageUsersService.find(principal.getName());
    }

    /**
     * User views details about book.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getVotedBook(@RequestParam(BOOK_ID_PARAM) Long bookId,
            Model model) {
        LOGGER.debug("Find book {}.", bookId);

        OfferedBook book = searchOfferedBooksService.findVoted(bookId);
        model.addAttribute("book", book);

        LOGGER.debug("Book has {} voters.", book.getVotersNum());
        return OFFERED_BOOK_PATH;
    }

    /**
     * User votes for book.
     */
    @RequestMapping(params = "vote", method = RequestMethod.POST)
    public String vote(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(CURRENT_USER_ATTR) User user)
            throws BookNotUpdatedException {
        LOGGER.debug("{} votes for book {}.", user.getUsername(), bookId);

        OfferedBook book = searchOfferedBooksService.findVoted(bookId);
        manageOfferedBooksService.vote(book, user);

        return redirectToOfferedBook(bookId);
    }

    /**
     * Manager adds a book to library.
     */
    @RequestMapping(params = "share", method = RequestMethod.POST)
    public String share(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(CURRENT_USER_ATTR) User user)
            throws BookNotUpdatedException {
        LOGGER.debug("{} shares book {}.", user.getUsername(), bookId);

        OfferedBook book = searchOfferedBooksService.findVoted(bookId);
        SharedBook sharedBook = manageOfferedBooksService.share(book, user);
        notifyUsersService.notifyAvailable(sharedBook,
                sharedBook.getSubscribers());

        return redirectToSharedBook(sharedBook.getId());
    }

    /**
     * Manager removes a book from list of offered books.
     */
    @RequestMapping(params = "remove", method = RequestMethod.POST)
    public String remove(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(CURRENT_USER_ATTR) User user)
            throws BookNotUpdatedException {
        LOGGER.debug("{} removes book {}.", user.getUsername(), bookId);

        OfferedBook book = searchOfferedBooksService.findVoted(bookId);
        manageOfferedBooksService.remove(book, user);

        return redirectToVote();
    }

    /**
     * If book was not updated, then notification should be shown.
     */
    @ExceptionHandler(BookNotUpdatedException.class)
    public String whenBookNotUpdated(BookNotUpdatedException exception,
            HttpServletRequest request) {
        Long bookId = getBookIdFromRequest(request);
        LOGGER.warn("Book {} was not updated, because: '{}'.", bookId,
                exception.getMessage());

        FlashMap attrs = RequestContextUtils.getOutputFlashMap(request);
        attrs.put("bookNotUpdated", true);

        return redirectToOfferedBook(bookId);
    }

    /**
     * If book was not found, then we redirect user to list of books.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public String whenBookNotFound(HttpServletRequest request) {
        Long bookId = getBookIdFromRequest(request);
        LOGGER.warn("Book {} was not found.", bookId);
        return redirectToVote();
    }

}
