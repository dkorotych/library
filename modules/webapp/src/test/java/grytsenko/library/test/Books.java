package grytsenko.library.test;

import static grytsenko.library.test.Users.manager;
import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;

/**
 * Utilities for work with books in tests.
 */
public final class Books {

    public static final long BOOK_ID = 100L;

    public static Book availableBook() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setStatus(BookStatus.AVAILABLE);
        book.setManagedBy(manager());
        return book;
    }

    public static Book reservedBook(User reservedBy) {
        Book book = availableBook();
        book.setStatus(BookStatus.RESERVED);
        book.setUsedBy(reservedBy);
        return book;
    }

    public static Book borrowedBook(User borrowedBy) {
        Book book = availableBook();
        book.setStatus(BookStatus.BORROWED);
        book.setUsedBy(borrowedBy);
        return book;
    }

    private Books() {
    }

}
