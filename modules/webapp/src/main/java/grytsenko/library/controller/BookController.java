package grytsenko.library.controller;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;
import grytsenko.library.service.BookNotUpdatedException;
import grytsenko.library.service.BookService;
import grytsenko.library.service.MailNotSentException;
import grytsenko.library.service.MailService;
import grytsenko.library.service.SearchService;
import grytsenko.library.service.UserService;

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
 * Manages requests for a single book.
 */
@Controller
@RequestMapping(value = "/book", params = "bookId")
@SessionAttributes({ "user" })
public class BookController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BookController.class);

    private UserService userService;
    private SearchService searchService;
    private BookService bookService;
    private MailService mailService;

    @Autowired
    public BookController(UserService userService, SearchService searchService,
            BookService bookService, MailService mailService) {
        this.userService = userService;
        this.searchService = searchService;
        this.bookService = bookService;
        this.mailService = mailService;
    }

    @ModelAttribute("user")
    public User currentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @ExceptionHandler(BookNotUpdatedException.class)
    public String whenBookNotUpdated(BookNotUpdatedException exception,
            HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        LOGGER.warn("Book {} was not updated, because: '{}'.", bookId,
                exception.getMessage());

        FlashMap flashAttrs = RequestContextUtils.getOutputFlashMap(request);
        flashAttrs.put("lastOperationFailed", true);

        return redirectToBook(bookId);
    }

    @ExceptionHandler(MailNotSentException.class)
    public String whenMailNotSent(MailNotSentException exception,
            HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        LOGGER.warn("Notification for book {} was not sent, because: '{}'.",
                bookId, exception.getMessage());

        return redirectToBook(bookId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getBook(@RequestParam("bookId") Long bookId, Model model) {
        Book book = searchService.find(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    /**
     * User reserves a book.
     */
    @RequestMapping(params = "reserve", method = RequestMethod.POST)
    public String reserve(@RequestParam("bookId") Long bookId,
            @ModelAttribute("user") User user) throws BookNotUpdatedException,
            MailNotSentException {
        LOGGER.debug("Reserve the book {}.", bookId);

        Book book = searchService.find(bookId);
        book = bookService.reserve(book, user);
        mailService.notifyReserved(book, user);

        return redirectToBook(book.getId());
    }

    /**
     * User or manager releases a book.
     */
    @RequestMapping(params = "release", method = RequestMethod.POST)
    public String release(@RequestParam("bookId") Long bookId,
            @ModelAttribute("user") User user) throws BookNotUpdatedException,
            MailNotSentException {
        LOGGER.debug("Release the book {}.", bookId);

        Book book = searchService.find(bookId);
        User wasReservedBy = book.getReservedBy();
        book = bookService.release(book, user);
        mailService.notifyReleased(book, wasReservedBy);

        return redirectToBook(book.getId());
    }

    /**
     * Manager takes out a book from library.
     */
    @RequestMapping(params = "takeOut", method = RequestMethod.POST)
    public String takeOut(@RequestParam("bookId") Long bookId,
            @ModelAttribute("user") User user) throws BookNotUpdatedException,
            MailNotSentException {
        LOGGER.debug("Take out the book {}.", bookId);

        Book book = searchService.find(bookId);
        book = bookService.takeOut(book, user);
        mailService.notifyBorrowed(book, book.getBorrowedBy());

        return redirectToBook(book.getId());
    }

    /**
     * Manager takes back a book to library.
     */
    @RequestMapping(params = "takeBack", method = RequestMethod.POST)
    public String takeBack(@RequestParam("bookId") Long bookId,
            @ModelAttribute("user") User user) throws BookNotUpdatedException,
            MailNotSentException {
        LOGGER.debug("Take back the book {}.", bookId);

        Book book = searchService.find(bookId);
        User wasBorrowedBy = book.getBorrowedBy();
        book = bookService.takeBack(book, user);
        mailService.notifyReturned(book, wasBorrowedBy);

        return redirectToBook(book.getId());
    }

    /**
     * Manager reminds that book is reserved or is borrowed by user.
     */
    @RequestMapping(params = "remind", method = RequestMethod.POST)
    public String remind(@RequestParam("bookId") Long bookId,
            @ModelAttribute("user") User use) throws MailNotSentException {
        LOGGER.debug("Remind about the book {}.", bookId);

        Book book = searchService.find(bookId);

        BookStatus bookStatus = book.getStatus();

        if (bookStatus == BookStatus.RESERVED) {
            mailService.notifyReserved(book, book.getReservedBy());
        } else if (bookStatus == BookStatus.BORROWED) {
            mailService.notifyBorrowed(book, book.getBorrowedBy());
        }

        return redirectToBook(bookId);
    }

    private static String redirectToBook(Long bookId) {
        return "redirect:/book?bookId=" + bookId;
    }

}
