package grytsenko.library.model;

import static grytsenko.library.test.Books.availableBook;
import static grytsenko.library.test.Books.borrowedBook;
import static grytsenko.library.test.Books.reservedBook;
import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.manager;
import static grytsenko.library.util.DateUtils.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

public class BookTests {

    @Test
    public void testReserve() throws Exception {
        Book book = availableBook();
        User reservedBy = guest();
        Date reservedAt = now();

        book.reserve(reservedBy, reservedAt);

        assertEquals(BookStatus.RESERVED, book.getStatus());
        assertEquals(reservedAt, book.getStatusChanged());

        assertEquals(reservedBy, book.getReservedBy());
        assertEquals(reservedAt, book.getReservedSince());

        assertNull(book.getBorrowedBy());
        assertNull(book.getBorrowedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testReserveNotAvailable() throws Exception {
        Book book = reservedBook(manager());
        book.reserve(guest(), now());
    }

    @Test
    public void testReleaseByUser() {
        User reservedBy = guest();
        Book book = reservedBook(reservedBy);
        Date releasedAt = now();

        book.release(reservedBy, releasedAt);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(releasedAt, book.getStatusChanged());

        assertNull(book.getReservedBy());
        assertNull(book.getReservedSince());

        assertNull(book.getBorrowedBy());
        assertNull(book.getBorrowedSince());
    }

    @Test
    public void testReleaseByManger() {
        User reservedBy = guest();
        Book book = reservedBook(reservedBy);
        User managedBy = book.getManagedBy();
        Date releasedAt = now();

        book.release(managedBy, releasedAt);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(releasedAt, book.getStatusChanged());
    }

    @Test(expected = IllegalStateException.class)
    public void testReleaseNotReserved() {
        Book book = availableBook();

        book.release(manager(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testReleaseNotAllowed() {
        User reservedBy = manager();
        Book book = reservedBook(reservedBy);

        book.release(guest(), now());
    }

    @Test
    public void testTakeOut() throws Exception {
        User reservedBy = guest();
        Book book = reservedBook(reservedBy);
        User manager = book.getManagedBy();
        Date borrowedAt = now();

        book.takeOut(manager, borrowedAt);

        assertEquals(BookStatus.BORROWED, book.getStatus());
        assertEquals(borrowedAt, book.getStatusChanged());

        assertNull(book.getReservedBy());
        assertNull(book.getReservedSince());

        assertEquals(reservedBy, book.getBorrowedBy());
        assertEquals(borrowedAt, book.getBorrowedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeOutNotReserved() throws Exception {
        Book book = availableBook();

        book.takeOut(book.getManagedBy(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeOutNotAllowed() throws Exception {
        Book book = reservedBook(guest());

        book.takeOut(guest(), now());
    }

    @Test
    public void testTakeBack() throws Exception {
        User borrowedBy = guest();
        Book book = borrowedBook(borrowedBy);
        User manager = book.getManagedBy();
        Date returnedAt = now();

        book.takeBack(manager, returnedAt);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(returnedAt, book.getStatusChanged());

        assertNull(book.getReservedBy());
        assertNull(book.getReservedSince());

        assertNull(book.getBorrowedBy());
        assertNull(book.getBorrowedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeBackNotBorrowed() throws Exception {
        Book book = reservedBook(guest());

        book.takeBack(book.getManagedBy(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeBackNotAllowed() throws Exception {
        Book book = reservedBook(guest());

        book.takeBack(guest(), now());
    }

}
