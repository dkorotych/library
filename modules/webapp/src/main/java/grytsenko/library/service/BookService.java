package grytsenko.library.service;

import static grytsenko.library.util.DateUtils.now;
import grytsenko.library.model.Book;
import grytsenko.library.model.SearchResults;
import grytsenko.library.model.User;
import grytsenko.library.repository.BookRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * Finds a book by its identifier.
     */
    public Book find(long bookId) {
        Book book = bookRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} was not found.", bookId);
            throw new BookNotFoundException();
        }

        return book;
    }

    /**
     * Finds all books.
     */
    public SearchResults<Book> findAll(int pageNum, int pageSize) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "The page number less than zero.");
        }

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Book> page = bookRepository.findAll(pageRequest);
        return toSearchResults(page);
    }

    private SearchResults<Book> toSearchResults(Page<Book> page) {
        SearchResults<Book> results = new SearchResults<>();
        results.setPageNum(page.getNumber());
        results.setPagesTotal(page.getTotalPages());
        results.setContent(page.getContent());
        return results;
    }

    /**
     * Finds books which are related to user.
     */
    public List<Book> findRelatedTo(User user) {
        return bookRepository.findRelatedTo(user);
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
