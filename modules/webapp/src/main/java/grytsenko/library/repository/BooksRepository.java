package grytsenko.library.repository;

import grytsenko.library.model.Book;
import grytsenko.library.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of books.
 */
public interface BooksRepository extends JpaRepository<Book, Long> {

    /**
     * Finds all books which are related to user.
     */
    List<Book> findByUsedBy(User user);

}
