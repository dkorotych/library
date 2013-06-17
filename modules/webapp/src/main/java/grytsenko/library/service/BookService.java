package grytsenko.library.service;

import static grytsenko.library.util.DateUtils.now;
import grytsenko.library.model.Book;
import grytsenko.library.model.User;
import grytsenko.library.repository.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages books in library.
 */
@Service
public class BookService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BookService.class);

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Reserves a book for user.
     */
    @Transactional
    public Book reserve(Book book, User user) throws BookNotUpdatedException {
        if (!book.canBeReserved()) {
            throw new BookNotUpdatedException("Book can not be reserved.");
        }
        LOGGER.debug("Book {} can be reserved.", book.getId());

        book.reserve(user, now());
        return save(book);
    }

    /**
     * Releases a book.
     */
    @Transactional
    public Book release(Book book, User user) throws BookNotUpdatedException {
        if (!book.canBeReleased(user)) {
            throw new BookNotUpdatedException("Book can not be released.");
        }
        LOGGER.debug("Book {} can be released.", book.getId());

        book.release(user, now());
        return save(book);
    }

    /**
     * Takes out a book from library.
     */
    @Transactional
    public Book takeOut(Book book, User user) throws BookNotUpdatedException {
        if (!book.canBeTakenOut(user)) {
            throw new BookNotUpdatedException("Book can not be taken out.");
        }
        LOGGER.debug("Book {} can be taken out.", book.getId());

        book.takeOut(user, now());
        return save(book);
    }

    /**
     * Takes back a book to library.
     */
    @Transactional
    public Book takeBack(Book book, User user) throws BookNotUpdatedException {
        if (!book.canBeTakenBack(user)) {
            throw new BookNotUpdatedException("Book can not be taken back.");
        }
        LOGGER.debug("Book {} can be taken back.", book.getId());

        book.takeBack(user, now());
        return save(book);
    }

    private Book save(Book book) throws BookNotUpdatedException {
        try {
            return bookRepository.saveAndFlush(book);
        } catch (Exception exception) {
            LOGGER.warn("Can not save the book {}, because: '{}'.",
                    book.getId(), exception.getMessage());

            throw new BookNotUpdatedException("Can not save the book.");
        }
    }

}
