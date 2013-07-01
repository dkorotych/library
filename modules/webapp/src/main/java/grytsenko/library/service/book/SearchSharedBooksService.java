package grytsenko.library.service.book;

import grytsenko.library.model.SearchResults;
import grytsenko.library.model.SharedBook;
import grytsenko.library.model.User;
import grytsenko.library.repository.SharedBooksRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Searches shared books.
 */
@Service
public class SearchSharedBooksService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchSharedBooksService.class);

    @Autowired
    SharedBooksRepository sharedBooksRepository;

    /**
     * Finds a book.
     */
    public SharedBook find(long bookId) {
        SharedBook book = sharedBooksRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} was not found.", bookId);
            throw new BookNotFoundException();
        }

        return book;
    }

    /**
     * Finds all books.
     */
    public SearchResults<SharedBook> findAll(int pageNum, int pageSize) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "The page number less than zero.");
        }

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<SharedBook> page = sharedBooksRepository.findAll(pageRequest);
        return SearchResults.create(page);
    }

    /**
     * Finds all books which are used by user.
     */
    public List<SharedBook> findUsedBy(User user) {
        return sharedBooksRepository.findByUsedBy(user);
    }

    /**
     * Finds all books which are expected by user.
     */
    public List<SharedBook> findExpectedBy(User subscriber) {
        return sharedBooksRepository.findBySubscriber(subscriber);
    }

}
