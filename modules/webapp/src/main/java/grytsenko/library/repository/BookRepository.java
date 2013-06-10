package grytsenko.library.repository;

import grytsenko.library.model.Book;
import grytsenko.library.model.BookStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of books.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds all books with the given status.
     */
    List<Book> findByStatus(BookStatus status);

    /**
     * Finds all books, that were reserved by user.
     */
    List<Book> findByReservedById(Long user);

    /**
     * Finds all books, that were borrowed by user.
     */
    List<Book> findByBorrowedById(Long user);

}
