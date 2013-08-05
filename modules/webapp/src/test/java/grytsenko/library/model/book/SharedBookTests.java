package grytsenko.library.model.book;

import static grytsenko.library.test.Books.availableBook;
import static grytsenko.library.test.Books.borrowedBook;
import static grytsenko.library.test.Books.reservedBook;
import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.manager;
import static grytsenko.library.util.DateUtils.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import grytsenko.library.model.user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class SharedBookTests {

    @Test
    public void testReserve() throws Exception {
        SharedBook book = availableBook();
        User reservedBy = guest();
        Date reservedAt = now();

        book.reserve(reservedBy, reservedAt);

        assertEquals(SharedBookStatus.RESERVED, book.getStatus());
        assertEquals(reservedAt, book.getStatusChanged());

        assertEquals(reservedBy, book.getUsedBy());
        assertEquals(reservedAt, book.getUsedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testReserveNotAvailable() throws Exception {
        SharedBook book = reservedBook(manager());
        book.reserve(guest(), now());
    }

    @Test
    public void testReleaseByUser() {
        User reservedBy = guest();
        SharedBook book = reservedBook(reservedBy);
        Date releasedAt = now();

        book.release(reservedBy, releasedAt);

        assertEquals(SharedBookStatus.AVAILABLE, book.getStatus());
        assertEquals(releasedAt, book.getStatusChanged());

        assertNull(book.getUsedBy());
        assertNull(book.getUsedSince());
    }

    @Test
    public void testReleaseByManger() {
        User reservedBy = guest();
        SharedBook book = reservedBook(reservedBy);
        User managedBy = book.getManagedBy();
        Date releasedAt = now();

        book.release(managedBy, releasedAt);

        assertEquals(SharedBookStatus.AVAILABLE, book.getStatus());
        assertEquals(releasedAt, book.getStatusChanged());
    }

    @Test(expected = IllegalStateException.class)
    public void testReleaseNotReserved() {
        SharedBook book = availableBook();

        book.release(manager(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testReleaseNotAllowed() {
        User reservedBy = manager();
        SharedBook book = reservedBook(reservedBy);

        book.release(guest(), now());
    }

    @Test
    public void testTakeOut() throws Exception {
        User reservedBy = guest();
        SharedBook book = reservedBook(reservedBy);
        User manager = book.getManagedBy();
        Date borrowedAt = now();

        book.takeOut(manager, borrowedAt);

        assertEquals(SharedBookStatus.BORROWED, book.getStatus());
        assertEquals(borrowedAt, book.getStatusChanged());

        assertEquals(reservedBy, book.getUsedBy());
        assertEquals(borrowedAt, book.getUsedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeOutNotReserved() throws Exception {
        SharedBook book = availableBook();

        book.takeOut(book.getManagedBy(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeOutNotAllowed() throws Exception {
        SharedBook book = reservedBook(guest());

        book.takeOut(guest(), now());
    }

    @Test
    public void testTakeBack() throws Exception {
        User borrowedBy = guest();
        SharedBook book = borrowedBook(borrowedBy);
        User manager = book.getManagedBy();
        Date returnedAt = now();

        book.takeBack(manager, returnedAt);

        assertEquals(SharedBookStatus.AVAILABLE, book.getStatus());
        assertEquals(returnedAt, book.getStatusChanged());

        assertNull(book.getUsedBy());
        assertNull(book.getUsedSince());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeBackNotBorrowed() throws Exception {
        SharedBook book = reservedBook(guest());

        book.takeBack(book.getManagedBy(), now());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeBackNotAllowed() throws Exception {
        SharedBook book = reservedBook(guest());

        book.takeBack(guest(), now());
    }

    @Test
    public void testUsedWithinOneDay() {
        SharedBook book = availableBook();
        book.reserve(guest(), now());

        assertEquals(1, book.usedWithin());
    }

    @Test
    public void testUsedWithinOneWeek() {
        SharedBook book = availableBook();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        Date weekAgo = calendar.getTime();
        book.reserve(guest(), weekAgo);

        assertEquals(8, book.usedWithin());
    }

    @Test(expected = IllegalStateException.class)
    public void testUsedWithinForAvailable() {
        SharedBook book = availableBook();

        book.usedWithin();
    }

}
