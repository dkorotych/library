package grytsenko.library.repository;

import grytsenko.library.model.SharedBook;
import grytsenko.library.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of shared books.
 */
public interface SharedBooksRepository extends JpaRepository<SharedBook, Long> {

    /**
     * Finds all books which are used by user.
     */
    List<SharedBook> findByUsedBy(User user);

}
