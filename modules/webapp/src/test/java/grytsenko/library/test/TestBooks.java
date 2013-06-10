package grytsenko.library.test;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;

/**
 * Utilities for work with books in tests.
 */
public final class TestBooks {

    /**
     * The default identifier of book.
     */
    public static final long BOOK_ID = 100L;

    /**
     * Creates an available book.
     */
    public static Book availableBook() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setStatus(BookStatus.AVAILABLE);
        return book;
    }

    /**
     * Creates a reserved book.
     * 
     * @param reservedBy
     *            the user, who reserved a book.
     */
    public static Book reservedBook(User reservedBy) {
        Book book = availableBook();
        book.setStatus(BookStatus.RESERVED);
        book.setReservedBy(reservedBy);
        return book;
    }

    /**
     * Creates a borrowed book.
     * 
     * @param borrowedBy
     *            the user, who borrowed a book.
     */
    public static Book borrowedBook(User borrowedBy) {
        Book book = availableBook();
        book.setStatus(BookStatus.BORROWED);
        book.setBorrowedBy(borrowedBy);
        return book;
    }

    private TestBooks() {
    }

}
