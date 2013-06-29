package grytsenko.library.repository;

import grytsenko.library.model.OfferedBook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository of shared books.
 */
public interface OfferedBooksRepository extends
        JpaRepository<OfferedBook, Long> {

    /**
     * Finds all offered books and orders them by number of votes.
     */
    @Query(value = "SELECT book FROM offered_books book LEFT JOIN book.votedUsers AS user GROUP BY book.id ORDER BY COUNT(user.id) DESC")
    Page<OfferedBook> findAllInOrderOfVotesNumber(Pageable pageable);

}
