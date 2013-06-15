package grytsenko.library.repository;

import grytsenko.library.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of books.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
