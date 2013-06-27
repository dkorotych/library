package grytsenko.library.repository;

import grytsenko.library.model.OfferedBook;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of shared books.
 */
public interface OfferedBooksRepository extends
        JpaRepository<OfferedBook, Long> {
}
