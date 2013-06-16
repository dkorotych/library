package grytsenko.library.service;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.SearchResults;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.BookRepository;

import java.text.MessageFormat;
import java.util.Date;
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
    public Book find(long bookId) throws BookServiceException {
        Book book = bookRepository.findOne(bookId);

        if (book == null) {
            String errorMessage = MessageFormat.format(
                    "Book {0} was not found.", bookId);
            LOGGER.warn(errorMessage);
            throw new BookServiceException(errorMessage);
        }

        return book;
    }

    /**
     * Finds a subset of books.
     */
    public SearchResults<Book> find(int pageNum, int pageSize) {
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
     * Finds all books which are related to user.
     */
    public List<Book> findRelatedTo(User user) {
        return bookRepository.findRelatedTo(user);
    }

    /**
     * Reserves a book for user.
     * 
     * @param book
     *            the book which is reserved by user.
     * @param user
     *            the user who reserves a book.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be reserved.
     */
    @Transactional(rollbackFor = { BookServiceException.class })
    public Book reserve(Book book, User user) throws BookServiceException {
        LOGGER.debug("Checking that the book {} can be reserved.", book.getId());

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookServiceException("Book is not available.");
        }

        LOGGER.debug("Book {} can be reserved.", book.getId());

        Date now = now();

        book.setStatus(BookStatus.RESERVED);
        book.setStatusChanged(now);

        book.setReservedBy(user);
        book.setReservedSince(now);

        return saveBook(book);
    }

    /**
     * Releases a book.
     * 
     * @param book
     *            the book which is released by user.
     * @param user
     *            the user who releases a book.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be released.
     */
    @Transactional(rollbackFor = { BookServiceException.class })
    public Book release(Book book, User user) throws BookServiceException {
        LOGGER.debug("Checking that the book {} can be released.", book.getId());

        if (book.getStatus() != BookStatus.RESERVED) {
            throw new BookServiceException("Book is not reserved.");
        }

        if (user.getRole() != UserRole.MANAGER
                && !user.getId().equals(book.getReservedBy().getId())) {
            throw new BookServiceException("Access denied.");
        }

        LOGGER.debug("Book {} can be released.", book.getId());

        Date now = now();

        book.setStatus(BookStatus.AVAILABLE);
        book.setStatusChanged(now);

        book.setReservedBy(null);
        book.setReservedSince(null);

        return saveBook(book);
    }

    /**
     * Takes out a book from library.
     * 
     * @param book
     *            the book which is taken out from library.
     * @param user
     *            the user who takes out a book from library.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be taken out.
     */
    @Transactional(rollbackFor = { BookServiceException.class })
    public Book takeOut(Book book, User user) throws BookServiceException {
        LOGGER.debug("Checking that the book {} can be borrowed.", book.getId());

        if (book.getStatus() != BookStatus.RESERVED) {
            throw new BookServiceException("Book is not reserved.");
        }

        if (user.getRole() != UserRole.MANAGER) {
            throw new BookServiceException("Access denied.");
        }

        LOGGER.debug("Book {} can be borrowed.", book.getId());

        Date now = now();

        book.setStatus(BookStatus.BORROWED);
        book.setStatusChanged(now);

        book.setBorrowedBy(book.getReservedBy());
        book.setBorrowedSince(now);

        book.setReservedBy(null);
        book.setReservedSince(null);

        return saveBook(book);
    }

    /**
     * Takes back a book to library.
     * 
     * @param book
     *            the book which is taken back to library.
     * @param user
     *            the user who takes back a book to library.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be taken back.
     */
    @Transactional(rollbackFor = { BookServiceException.class })
    public Book takeBack(Book book, User user) throws BookServiceException {
        LOGGER.debug("Checking that the book {} can be returned.", book.getId());

        if (book.getStatus() != BookStatus.BORROWED) {
            throw new BookServiceException("Book is not borrowed.");
        }

        if (user.getRole() != UserRole.MANAGER) {
            throw new BookServiceException("Access denied.");
        }

        LOGGER.debug("Book {} can be returned.", book.getId());

        Date now = now();

        book.setStatus(BookStatus.AVAILABLE);
        book.setStatusChanged(now);

        book.setBorrowedBy(null);
        book.setBorrowedSince(null);

        book.setManagedBy(user);
        book.setManagedSince(now);

        return saveBook(book);
    }

    private Book saveBook(Book book) throws BookServiceException {
        try {
            return bookRepository.saveAndFlush(book);
        } catch (Exception exception) {
            LOGGER.warn("Can not save the book {}, because: '{}'.",
                    book.getId(), exception.getMessage());

            throw new BookServiceException("Can not save the book.");
        }
    }

    private Date now() {
        return new Date();
    }

}
