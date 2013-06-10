package grytsenko.library.service;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookFilter;
import grytsenko.library.model.BookStatus;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages books in library.
 */
@Service
public class BookService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BookService.class);

    private BookRepository bookRepository;

    /**
     * Creates and initializes a service.
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Finds books.
     * 
     * @param filter
     *            the filter for search.
     * @param user
     *            the user who searches books.
     * 
     * @return list of found books.
     * 
     * @throws BookServiceException
     *             if books are not accessible.
     */
    public List<Book> find(BookFilter filter, User user)
            throws BookServiceException {
        Long userId = user.getId();
        switch (filter) {
        case RELATED:
            List<Book> related = new ArrayList<>();
            List<Book> reserved = bookRepository.findByReservedById(userId);
            related.addAll(reserved);
            List<Book> borrowed = bookRepository.findByBorrowedById(userId);
            related.addAll(borrowed);
            return related;
        case AVAILABLE:
            return bookRepository.findByStatus(BookStatus.AVAILABLE);
        case ALL:
        default:
            return bookRepository.findAll();
        }
    }

    /**
     * Reserves a book for user.
     * 
     * @param bookId
     *            the identifier of reserved book.
     * @param user
     *            the user who reserves a book.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be reserved.
     */
    public Book reserve(long bookId, User user) throws BookServiceException {
        Book book = getBook(bookId);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookServiceException("Book is not available.");
        }

        Date now = now();

        book.setStatus(BookStatus.RESERVED);
        book.setStatusChanged(now);

        book.setReservedBy(user);
        book.setReservedSince(now);

        return bookRepository.saveAndFlush(book);
    }

    /**
     * Releases a book.
     * 
     * @param bookId
     *            the identifier of book.
     * @param user
     *            the user who releases a book.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be released.
     */
    public Book release(long bookId, User user) throws BookServiceException {
        Book book = getBook(bookId);

        if (book.getStatus() != BookStatus.RESERVED) {
            throw new BookServiceException("Book is not reserved.");
        }

        if (user.getRole() != UserRole.MANAGER
                && !user.getId().equals(book.getReservedBy().getId())) {
            throw new BookServiceException("Access denied.");
        }

        Date now = now();

        book.setStatus(BookStatus.AVAILABLE);
        book.setStatusChanged(now);

        book.setReservedBy(null);
        book.setReservedSince(null);

        return bookRepository.saveAndFlush(book);
    }

    /**
     * Takes out a book from library.
     * 
     * @param bookId
     *            the identifier of book.
     * @param user
     *            the user who takes out a book from library.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be taken out.
     */
    public Book takeOut(long bookId, User user) throws BookServiceException {
        Book book = getBook(bookId);

        if (book.getStatus() != BookStatus.RESERVED) {
            throw new BookServiceException("Book is not reserved.");
        }

        if (user.getRole() != UserRole.MANAGER) {
            throw new BookServiceException("Access denied.");
        }

        Date now = now();

        book.setStatus(BookStatus.BORROWED);
        book.setStatusChanged(now);

        book.setBorrowedBy(book.getReservedBy());
        book.setBorrowedSince(now);

        book.setReservedBy(null);
        book.setReservedSince(null);

        return bookRepository.saveAndFlush(book);
    }

    /**
     * Takes back a book to library.
     * 
     * @param bookId
     *            the identifier of book.
     * @param user
     *            the user who takes back a book to library.
     * 
     * @return updated book.
     * 
     * @throws BookServiceException
     *             if book cannot be taken back.
     */
    public Book takeBack(long bookId, User user) throws BookServiceException {
        Book book = getBook(bookId);

        if (book.getStatus() != BookStatus.BORROWED) {
            throw new BookServiceException("Book is not borrowed.");
        }

        if (user.getRole() != UserRole.MANAGER) {
            throw new BookServiceException("Access denied.");
        }

        Date now = now();

        book.setStatus(BookStatus.AVAILABLE);
        book.setStatusChanged(now);

        book.setBorrowedBy(null);
        book.setBorrowedSince(null);

        book.setManagedBy(user);
        book.setManagedSince(now);

        return bookRepository.saveAndFlush(book);
    }

    private Book getBook(long bookId) throws BookServiceException {
        Book book = bookRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} wasn't found in repository.", bookId);
            throw new BookServiceException("Book not found.");
        }

        return book;
    }

    private Date now() {
        return new Date();
    }

}
