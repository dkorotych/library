package grytsenko.library.controller;

import static grytsenko.library.test.TestBooks.availableBook;
import static grytsenko.library.test.TestBooks.borrowedBook;
import static grytsenko.library.test.TestBooks.reservedBook;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.manager;
import static org.mockito.Matchers.any;
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

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BookControllerTests {

    BookService bookService;
    BookController bookController;

    @Before
    public void prepare() {
        // Setup SUT.
        bookService = mock(BookService.class);

        bookController = new BookController(null, bookService);
    }

    /**
     * User reserves a book.
     */
    @Test
    public void testReserve() throws Exception {
        // Setup data.
        User guest = guest();
        Book reservedBook = reservedBook(guest);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(reservedBook).when(bookService).reserve(any(Book.class),
                any(User.class));

        // Execute.
        bookController.reserve(reservedBook, guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(reservedBook, guest);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * User tried to reserve a book, but error has occurred.
     */
    @Test
    public void testReserveFailed() throws Exception {
        // Setup data.
        User guest = guest();
        Book reservedBook = reservedBook(guest);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).reserve(
                any(Book.class), any(User.class));

        // Execute.
        bookController.reserve(reservedBook, guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(reservedBook, guest);
        verifyNoMoreInteractions(bookService);

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
        User guest = guest();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(availableBook).when(bookService).release(any(Book.class),
                any(User.class));

        // Execute.
        bookController.release(availableBook, guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).release(availableBook, guest);
        verifyNoMoreInteractions(bookService);

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
        Book borrowedBook = borrowedBook(guest());
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(borrowedBook).when(bookService).takeOut(any(Book.class),
                any(User.class));

        // Execute.
        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager tried to take out a book, but error has occurred.
     */
    @Test
    public void testTakeOutFailed() throws Exception {
        // Setup data.
        User manager = manager();
        Book borrowedBook = borrowedBook(guest());
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeOut(
                any(Book.class), any(User.class));

        // Execute.
        bookController.takeOut(borrowedBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(borrowedBook, manager);
        verifyNoMoreInteractions(bookService);

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
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(availableBook).when(bookService).takeBack(any(Book.class),
                any(User.class));

        // Execute.
        bookController.takeBack(availableBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(availableBook, manager);
        verifyNoMoreInteractions(bookService);

        verifyZeroInteractions(redirectAttributes);
    }

    /**
     * Manager tried to take back a book, but error has occurred.
     */
    @Test
    public void testTakeBackFailed() throws Exception {
        // Setup data.
        User manager = manager();
        Book availableBook = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeBack(
                any(Book.class), any(User.class));

        // Execute.
        bookController.takeBack(availableBook, manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(availableBook, manager);
        verifyNoMoreInteractions(bookService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

}
