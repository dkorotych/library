package grytsenko.library.controller;

import static grytsenko.library.test.TestBooks.availableBook;
import static grytsenko.library.test.TestBooks.borrowedBook;
import static grytsenko.library.test.TestBooks.reservedBook;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.manager;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import grytsenko.library.model.Book;
import grytsenko.library.model.BookFilter;
import grytsenko.library.model.User;
import grytsenko.library.service.BookService;
import grytsenko.library.service.BookServiceException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Test for {@link BooksController}.
 */
public class BooksControllerTests {

    BookService bookService;
    BooksController booksController;

    @Before
    public void prepare() {
        // Setup SUT.
        bookService = mock(BookService.class);

        booksController = new BooksController(null, bookService);
    }

    /**
     * User selects new filter.
     */
    @Test
    public void testFilter() throws Exception {
        // Setup data.
        String selectedFilter = "RELATED";
        Model model = mock(Model.class);

        // Setup behavior.

        // Execute.
        booksController.filter(model, selectedFilter);

        // Verify behavior.
        verify(model).addAttribute("filter", BookFilter.RELATED);
        verifyNoMoreInteractions(model);
    }

    /**
     * User selects filter, that is not supported.
     */
    @Test
    public void testFilterNotSupported() throws Exception {
        // Setup data.
        String selectedFilter = "UNSUPPORTED";
        Model model = mock(Model.class);

        // Setup behavior.

        // Execute.
        booksController.filter(model, selectedFilter);

        // Verify behavior.
        verify(model).addAttribute("filter", BookFilter.ALL);
        verifyNoMoreInteractions(model);
    }

    /**
     * User reserves a book.
     */
    @Test
    public void testReserve() throws Exception {
        // Setup data.
        User guest = guest();
        Book book = reservedBook(guest);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(book).when(bookService).reserve(anyLong(), any(User.class));

        // Execute.
        booksController.reserve(book.getId(), guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(book.getId(), guest);
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
        Book book = reservedBook(guest);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).reserve(
                anyLong(), any(User.class));

        // Execute.
        booksController.reserve(book.getId(), guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).reserve(book.getId(), guest);
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
        Book book = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(book).when(bookService).release(anyLong(), any(User.class));

        // Execute.
        booksController.release(book.getId(), guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).release(book.getId(), guest);
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
        Book book = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).release(
                anyLong(), any(User.class));

        // Execute.
        booksController.release(book.getId(), guest, redirectAttributes);

        // Verify behavior.
        verify(bookService).release(book.getId(), guest);
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
        Book book = borrowedBook(guest());
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(book).when(bookService).takeOut(anyLong(), any(User.class));

        // Execute.
        booksController.takeOut(book.getId(), manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(book.getId(), manager);
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
        Book book = borrowedBook(guest());
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeOut(
                anyLong(), any(User.class));

        // Execute.
        booksController.takeOut(book.getId(), manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeOut(book.getId(), manager);
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
        Book book = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doReturn(book).when(bookService).takeBack(anyLong(), any(User.class));

        // Execute.
        booksController.takeBack(book.getId(), manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(book.getId(), manager);
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
        Book book = availableBook();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup behavior.
        doThrow(new BookServiceException()).when(bookService).takeBack(
                anyLong(), any(User.class));

        // Execute.
        booksController.takeBack(book.getId(), manager, redirectAttributes);

        // Verify behavior.
        verify(bookService).takeBack(book.getId(), manager);
        verifyNoMoreInteractions(bookService);

        verify(redirectAttributes).addFlashAttribute("lastOperationFailed",
                true);
        verifyNoMoreInteractions(redirectAttributes);
    }

}
