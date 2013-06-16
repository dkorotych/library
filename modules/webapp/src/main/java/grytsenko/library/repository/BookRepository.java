package grytsenko.library.repository;

import grytsenko.library.model.Book;
import grytsenko.library.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of books.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByReservedBy(User user);

    List<Book> findByBorrowedBy(User user);

}
