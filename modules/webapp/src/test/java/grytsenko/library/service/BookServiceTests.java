package grytsenko.library.service;

import static grytsenko.library.test.MockitoUtils.doReturnFirstArgument;
import static grytsenko.library.test.TestBooks.BOOK_ID;
import static grytsenko.library.test.TestBooks.availableBook;
import static grytsenko.library.test.TestBooks.borrowedBook;
import static grytsenko.library.test.TestBooks.reservedBook;
import static grytsenko.library.test.TestUsers.GUEST_NAME;
import static grytsenko.library.test.TestUsers.MANAGER_NAME;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.manager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.BookRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link BookService}.
 */
public class BookServiceTests {

    BookRepository bookRepository;

    BookService bookService;

    @Before
    public void prepare() {
        // Setup SUT.
        bookRepository = mock(BookRepository.class);

        bookService = new BookService(bookRepository);

        // Setup behavior.
        doReturnFirstArgument().when(bookRepository).saveAndFlush(
                any(Book.class));
    }

    /**
     * User reserves a book.
     */
    @Test
    public void testReserve() throws Exception {
        // Setup data.
        User guest = guest();
        Book storedBook = availableBook();

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        Book updatedBook = bookService.reserve(BOOK_ID, guest);

        // Verify state.
        assertEquals(BookStatus.RESERVED, updatedBook.getStatus());
        assertEquals(updatedBook.getReservedBy(), guest.getUsername());
        assertNotNull(updatedBook.getReservedSince());

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verify(bookRepository).saveAndFlush(storedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * User reserves a book, but it is not available.
     */
    @Test
    public void testReserveNotAvailable() throws Exception {
        // Setup data.
        User guest = guest();
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.reserve(BOOK_ID, guest);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * User, who has reserved book, releases it.
     */
    @Test
    public void testReleaseByUser() throws Exception {
        // Setup data.
        User guest = guest();
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        Book updatedBook = bookService.release(BOOK_ID, guest);

        // Verify state.
        assertEquals(BookStatus.AVAILABLE, updatedBook.getStatus());
        assertNull(updatedBook.getReservedBy());

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verify(bookRepository).saveAndFlush(storedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Manager releases a book.
     */
    @Test
    public void testReleaseByManager() throws Exception {
        // Setup data.
        User manager = manager();
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        Book updatedBook = bookService.release(BOOK_ID, manager);

        // Verify state.
        assertEquals(BookStatus.AVAILABLE, updatedBook.getStatus());
        assertNull(updatedBook.getReservedBy());

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verify(bookRepository).saveAndFlush(storedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Other user (not a one, who has reserved a book, and not a manager)
     * releases a book.
     */
    @Test
    public void testReleaseNotAllowed() {
        // Setup data.
        User other = new User();
        other.setUsername("other");
        other.setRole(UserRole.USER);
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.release(BOOK_ID, other);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Manager releases a book, which was not reserved.
     */
    @Test
    public void testReleaseNotReserved() throws Exception {
        // Setup data.
        User manager = manager();
        Book storedBook = availableBook();

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.release(BOOK_ID, manager);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * When user borrows a reserved book, manager takes out this book from
     * library.
     */
    @Test
    public void testTakeOut() throws Exception {
        // Setup data.
        User manager = manager();
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        Book updatedBook = bookService.takeOut(BOOK_ID, manager);

        // Verify state.
        assertEquals(BookStatus.BORROWED, updatedBook.getStatus());

        assertEquals(GUEST_NAME, updatedBook.getBorrowedBy());
        assertNotNull(updatedBook.getBorrowedSince());

        assertNull(updatedBook.getReservedBy());
        assertNull(updatedBook.getReservedSince());

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verify(bookRepository).saveAndFlush(storedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Some user, but not a manager, tries to take out a book.
     */
    @Test
    public void testTakeOutNotAllowed() throws Exception {
        // Setup data.
        User guest = guest();
        Book storedBook = reservedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.takeOut(BOOK_ID, guest);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Manager tries to take out a book, that is not reserved.
     */
    @Test
    public void testTakeOutNotReserved() {
        // Setup data.
        User manager = manager();
        Book storedBook = availableBook();

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.takeOut(BOOK_ID, manager);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Manager takes back a book to library.
     */
    @Test
    public void testTakeBack() throws Exception {
        // Setup data.
        User manager = manager();
        Book storedBook = borrowedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        Book updatedBook = bookService.takeBack(BOOK_ID, manager);

        // Verify state.
        assertEquals(BookStatus.AVAILABLE, updatedBook.getStatus());

        assertEquals(MANAGER_NAME, updatedBook.getManagedBy());
        assertNotNull(updatedBook.getManagedSince());

        assertNull(updatedBook.getBorrowedBy());
        assertNull(updatedBook.getBorrowedSince());

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verify(bookRepository).saveAndFlush(storedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Some user, but not a manager, tries to take back a book.
     */
    @Test
    public void testTakeBackNotAllowed() throws Exception {
        // Setup data.
        User guest = guest();
        Book storedBook = borrowedBook(GUEST_NAME);

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.takeBack(BOOK_ID, guest);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    /**
     * Manager tries to take out a book, that is not borrowed.
     */
    @Test
    public void testTakeBackNotBorrowed() {
        // Setup data.
        User manager = manager();
        Book storedBook = availableBook();

        // Setup behavior.
        doReturn(storedBook).when(bookRepository).findOne(anyLong());

        // Execute.
        try {
            bookService.takeBack(BOOK_ID, manager);
            fail();
        } catch (BookServiceException exception) {
        }

        // Verify behavior.
        verify(bookRepository).findOne(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

}
