package grytsenko.library.controller;

import static grytsenko.library.test.Books.availableBook;
import static grytsenko.library.test.Books.borrowedBook;
import static grytsenko.library.test.Books.reservedBook;
import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.manager;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import grytsenko.library.model.Book;
import grytsenko.library.model.User;
import grytsenko.library.service.BookNotUpdatedException;
import grytsenko.library.service.BookService;
import grytsenko.library.service.MailNotSentException;
import grytsenko.library.service.MailService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BookControllerTests {

    BookService bookService;
    MailService mailService;

    BookController bookController;

    @Before
    public void prepare() throws Exception {
        bookService = mock(BookService.class);
        mailService = mock(MailService.class);

        bookController = new BookController(null, bookService, mailService);

        doNothing().when(mailService).notifyReserved(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyReleased(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyBorrowed(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyReturned(any(Book.class),
                any(User.class));
    }

    @Test
    public void testReserve() throws Exception {
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doReturn(reservedBook).when(bookService).reserve(any(Book.class),
                any(User.class));

        bookController.reserve(reservedBook, reservedBy, redirectAttributes);

        verify(bookService).reserve(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testReserveFailed() throws Exception {
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doThrow(new BookNotUpdatedException()).when(bookService).reserve(
                any(Book.class), any(User.class));

        bookController.reserve(reservedBook, reservedBy, redirectAttributes);

        verify(bookService).reserve(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    @Test
    public void testRelease() throws Exception {
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doReturn(reservedBook).when(bookService).release(any(Book.class),
                any(User.class));

        bookController.release(reservedBook, reservedBy, redirectAttributes);

        verify(bookService).release(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReleased(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testReleaseFailed() throws Exception {
        User guest = guest();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doThrow(new BookNotUpdatedException()).when(bookService).release(
                any(Book.class), any(User.class));

        bookController.release(availableBook, guest, redirectAttributes);

        verify(bookService).release(availableBook, guest);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    @Test
    public void testTakeOut() throws Exception {
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doReturn(borrowedBook).when(bookService).takeOut(any(Book.class),
                any(User.class));

        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyBorrowed(borrowedBook, borrowedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testTakeOutFailed() throws Exception {
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doThrow(new BookNotUpdatedException()).when(bookService).takeOut(
                any(Book.class), any(User.class));

        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    @Test
    public void testTakeBack() throws Exception {
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doReturn(borrowedBook).when(bookService).takeBack(any(Book.class),
                any(User.class));

        bookController.takeBack(borrowedBook, manager, redirectAttributes);

        verify(bookService).takeBack(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReturned(borrowedBook, manager);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testTakeBackFailed() throws Exception {
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doThrow(new BookNotUpdatedException()).when(bookService).takeBack(
                any(Book.class), any(User.class));

        bookController.takeBack(borrowedBook, manager, redirectAttributes);

        verify(bookService).takeBack(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    @Test
    public void testRemindAboutReserved() throws Exception {
        User manager = manager();
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        bookController.remind(reservedBook, manager, redirectAttributes);

        verifyZeroInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testRemindAboutBorrowed() throws Exception {
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        bookController.remind(borrowedBook, manager, redirectAttributes);

        verifyZeroInteractions(bookService);

        verify(mailService).notifyBorrowed(borrowedBook, borrowedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testRemindAboutAvailable() throws Exception {
        User manager = manager();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        bookController.remind(availableBook, manager, redirectAttributes);

        verifyZeroInteractions(bookService);

        verifyZeroInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    @Test
    public void testRemindFailed() throws Exception {
        User manager = manager();
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doThrow(new MailNotSentException()).when(mailService).notifyReserved(
                any(Book.class), any(User.class));

        bookController.remind(reservedBook, manager, redirectAttributes);

        verifyZeroInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

}
