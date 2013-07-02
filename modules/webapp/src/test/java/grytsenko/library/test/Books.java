package grytsenko.library.test;

import static grytsenko.library.test.Users.manager;
import grytsenko.library.model.book.OfferedBook;
import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.book.SharedBookStatus;
import grytsenko.library.model.user.User;

import java.util.ArrayList;

/**
 * Utilities for work with books in tests.
 */
public final class Books {

    public static final long BOOK_ID = 100L;

    public static SharedBook availableBook() {
        SharedBook book = new SharedBook();
        book.setId(BOOK_ID);
        book.setStatus(SharedBookStatus.AVAILABLE);
        book.setManagedBy(manager());
        return book;
    }

    public static SharedBook reservedBook(User reservedBy) {
        SharedBook book = availableBook();
        book.setStatus(SharedBookStatus.RESERVED);
        book.setUsedBy(reservedBy);
        return book;
    }

    public static SharedBook borrowedBook(User borrowedBy) {
        SharedBook book = availableBook();
        book.setStatus(SharedBookStatus.BORROWED);
        book.setUsedBy(borrowedBy);
        return book;
    }

    public static OfferedBook offeredBook() {
        OfferedBook book = new OfferedBook();
        book.setId(BOOK_ID);
        book.setVoters(new ArrayList<User>());
        return book;
    }

    private Books() {
    }

}
