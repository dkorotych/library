package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;
import grytsenko.library.service.BookNotUpdatedException;
import grytsenko.library.service.ManageBooksService;
import grytsenko.library.service.ManageUsersService;
import grytsenko.library.service.NotifyUsersService;
import grytsenko.library.service.SearchBooksService;
import grytsenko.library.service.UserNotNotifiedException;

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
 * Processes requests for managing a book.
 */
@Controller
@RequestMapping(value = "/book", params = "bookId")
@SessionAttributes({ "user" })
public class BookController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BookController.class);

    private static final String USER_ATTR = "user";
    private static final String BOOK_ID_PARAM = "bookId";

    @Autowired
    ManageUsersService manageUsersService;
    @Autowired
    NotifyUsersService notifyUsersService;

    @Autowired
    SearchBooksService searchBooksService;
    @Autowired
    ManageBooksService manageBooksService;

    @ModelAttribute(USER_ATTR)
    public User currentUser(Principal principal) {
        return manageUsersService.find(principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getBook(@RequestParam(BOOK_ID_PARAM) Long bookId, Model model) {
        Book book = searchBooksService.find(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    /**
     * User reserves a book.
     */
    @RequestMapping(params = "reserve", method = RequestMethod.POST)
    public String reserve(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws BookNotUpdatedException, UserNotNotifiedException {
        LOGGER.debug("Reserve the book {}.", bookId);

        Book book = searchBooksService.find(bookId);
        book = manageBooksService.reserve(book, user);
        notifyUsersService.notifyReserved(book, user);

        return redirectToBook(bookId);
    }

    /**
     * User or manager releases a book.
     */
    @RequestMapping(params = "release", method = RequestMethod.POST)
    public String release(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws BookNotUpdatedException, UserNotNotifiedException {
        LOGGER.debug("Release the book {}.", bookId);

        Book book = searchBooksService.find(bookId);
        User wasReservedBy = book.getReservedBy();
        book = manageBooksService.release(book, user);
        notifyUsersService.notifyReleased(book, wasReservedBy);

        return redirectToBook(bookId);
    }

    /**
     * Manager takes out a book from library.
     */
    @RequestMapping(params = "takeOut", method = RequestMethod.POST)
    public String takeOut(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws BookNotUpdatedException, UserNotNotifiedException {
        LOGGER.debug("Take out the book {}.", bookId);

        Book book = searchBooksService.find(bookId);
        book = manageBooksService.takeOut(book, user);
        notifyUsersService.notifyBorrowed(book, book.getBorrowedBy());

        return redirectToBook(bookId);
    }

    /**
     * Manager takes back a book to library.
     */
    @RequestMapping(params = "takeBack", method = RequestMethod.POST)
    public String takeBack(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws BookNotUpdatedException, UserNotNotifiedException {
        LOGGER.debug("Take back the book {}.", bookId);

        Book book = searchBooksService.find(bookId);
        User wasBorrowedBy = book.getBorrowedBy();
        book = manageBooksService.takeBack(book, user);
        notifyUsersService.notifyReturned(book, wasBorrowedBy);

        return redirectToBook(bookId);
    }

    /**
     * Manager reminds that book is reserved or is borrowed by user.
     */
    @RequestMapping(params = "remind", method = RequestMethod.POST)
    public String remind(@RequestParam(BOOK_ID_PARAM) Long bookId,
            @ModelAttribute(USER_ATTR) User user)
            throws UserNotNotifiedException {
        LOGGER.debug("Remind about the book {}.", bookId);

        Book book = searchBooksService.find(bookId);
        if (!book.isManagedBy(user)) {
            LOGGER.debug("Book {} is not managed by {}.", bookId,
                    user.getUsername());
            return redirectToBook(bookId);
        }

        BookStatus bookStatus = book.getStatus();

        if (bookStatus == BookStatus.RESERVED) {
            notifyUsersService.notifyReserved(book, book.getReservedBy());
        } else if (bookStatus == BookStatus.BORROWED) {
            notifyUsersService.notifyBorrowed(book, book.getBorrowedBy());
        }

        return redirectToBook(bookId);
    }

    @ExceptionHandler(BookNotUpdatedException.class)
    public String whenBookNotUpdated(BookNotUpdatedException exception,
            HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter(BOOK_ID_PARAM));
        LOGGER.warn("Book {} was not updated, because: '{}'.", bookId,
                exception.getMessage());

        FlashMap flashAttrs = RequestContextUtils.getOutputFlashMap(request);
        flashAttrs.put("bookNotUpdated", true);

        return redirectToBook(bookId);
    }

    @ExceptionHandler(UserNotNotifiedException.class)
    public String whenUserNotNotified(UserNotNotifiedException exception,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_ATTR);
        LOGGER.warn("User {} was not notified, because: '{}'.", user.getId(),
                exception.getMessage());

        FlashMap flashAttrs = RequestContextUtils.getOutputFlashMap(request);
        flashAttrs.put("userNotNotified", true);

        Long bookId = Long.parseLong(request.getParameter(BOOK_ID_PARAM));
        return redirectToBook(bookId);
    }

    private static String redirectToBook(Long bookId) {
        return "redirect:/book?bookId=" + bookId;
    }

}
