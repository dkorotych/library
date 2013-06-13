package grytsenko.library.controller;

import static grytsenko.library.test.TestBooks.availableBook;
import static grytsenko.library.test.TestBooks.borrowedBook;
import static grytsenko.library.test.TestBooks.reservedBook;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.manager;
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
import grytsenko.library.service.BookService;
import grytsenko.library.service.BookServiceException;
import grytsenko.library.service.MailService;
import grytsenko.library.service.MailServiceException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BookControllerTests {

    BookService bookService;
    MailService mailService;

    BookController bookController;

    @Before
    public void prepare() throws Exception {
        // Setup SUT.
        bookService = mock(BookService.class);
        mailService = mock(MailService.class);

        bookController = new BookController(null, bookService, mailService);

        // Setup behavior.
        doNothing().when(mailService).notifyReserved(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyReleased(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyBorrowed(any(Book.class),
                any(User.class));
        doNothing().when(mailService).notifyReturned(any(Book.class),
                any(User.class));
    }

    /**
     * User reserves a book.
     */
    @Test
    public void testReserve() throws Exception {
        // Setup data.
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(reservedBook).when(bookService).reserve(any(Book.class),
                any(User.class));

        // Execute.
        bookController.reserve(reservedBook, reservedBy, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * User tried to reserve a book, but error has occurred.
     */
    @Test
    public void testReserveFailed() throws Exception {
        // Setup data.
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).reserve(
                any(Book.class), any(User.class));

        // Execute.
        bookController.reserve(reservedBook, reservedBy, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    /**
     * User releases a book.
     */
    @Test
    public void testRelease() throws Exception {
        // Setup data.
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(reservedBook).when(bookService).release(any(Book.class),
                any(User.class));

        // Execute.
        bookController.release(reservedBook, reservedBy, redirectAttributes);

        // Verify behavior.
        verify(bookService).release(reservedBook, reservedBy);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReleased(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * User tried to release a book, but error has occurred.
     */
    @Test
    public void testReleaseFailed() throws Exception {
        // Setup data.
        User guest = guest();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).release(
                any(Book.class), any(User.class));

        // Execute.
        bookController.release(availableBook, guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).release(availableBook, guest);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    /**
     * Manager takes out a book.
     */
    @Test
    public void testTakeOut() throws Exception {
        // Setup data.
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(borrowedBook).when(bookService).takeOut(any(Book.class),
                any(User.class));

        // Execute.
        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyBorrowed(borrowedBook, borrowedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager tried to take out a book, but error has occurred.
     */
    @Test
    public void testTakeOutFailed() throws Exception {
        // Setup data.
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeOut(
                any(Book.class), any(User.class));

        // Execute.
        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    /**
     * Manager takes back a book.
     */
    @Test
    public void testTakeBack() throws Exception {
        // Setup data.
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(borrowedBook).when(bookService).takeBack(any(Book.class),
                any(User.class));

        // Execute.
        bookController.takeBack(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verify(mailService).notifyReturned(borrowedBook, manager);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager tried to take back a book, but error has occurred.
     */
    @Test
    public void testTakeBackFailed() throws Exception {
        // Setup data.
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeBack(
                any(Book.class), any(User.class));

        // Execute.
        bookController.takeBack(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

    /**
     * Manager reminds a user about the reserved book.
     */
    @Test
    public void testRemindAboutReserved() throws Exception {
        // Setup data.
        User manager = manager();
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Execute.
        bookController.remind(reservedBook, manager, redirectAttributes);

        // Verify behavior.
        verifyZeroInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager reminds a user about the borrowed book.
     */
    @Test
    public void testRemindAboutBorrowed() throws Exception {
        // Setup data.
        User manager = manager();
        User borrowedBy = guest();
        Book borrowedBook = borrowedBook(borrowedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Execute.
        bookController.remind(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verifyZeroInteractions(bookService);

        verify(mailService).notifyBorrowed(borrowedBook, borrowedBy);
        verifyNoMoreInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager reminds a user about the available book.
     */
    @Test
    public void testRemindAboutAvailable() throws Exception {
        // Setup data.
        User manager = manager();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Execute.
        bookController.remind(availableBook, manager, redirectAttributes);

        // Verify behavior.
        verifyZeroInteractions(bookService);

        verifyZeroInteractions(mailService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager tried to remind, but error has occurred.
     */
    @Test
    public void testRemindFailed() throws Exception {
        // Setup data.
        User manager = manager();
        User reservedBy = guest();
        Book reservedBook = reservedBook(reservedBy);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new MailServiceException()).when(mailService).notifyReserved(
                any(Book.class), any(User.class));

        // Execute.
        bookController.remind(reservedBook, manager, redirectAttributes);

        // Verify behavior.
        verifyZeroInteractions(bookService);

        verify(mailService).notifyReserved(reservedBook, reservedBy);
        verifyNoMoreInteractions(mailService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

}
