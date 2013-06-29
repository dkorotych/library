package grytsenko.library.service;

import grytsenko.library.model.OfferedBook;
import grytsenko.library.model.SearchResults;
import grytsenko.library.repository.OfferedBooksRepository;

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
public class SearchOfferedBooksService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchOfferedBooksService.class);

    @Autowired
    OfferedBooksRepository offeredBooksRepository;

    /**
     * Finds a book.
     */
    public OfferedBook findVoted(long bookId) {
        OfferedBook book = offeredBooksRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} was not found.", bookId);
            throw new BookNotFoundException();
        }
        if (book.isDeleted()) {
            LOGGER.warn("Book {} is deleted.", bookId);
            throw new BookNotFoundException("Book is deleted.");
        }

        return book;
    }

    /**
     * Finds all books.
     */
    public SearchResults<OfferedBook> findAll(Integer pageNum, int pageSize) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "The page number less than zero.");
        }

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<OfferedBook> page = offeredBooksRepository
                .findAllVoted(pageRequest);
        return SearchResults.create(page);
    }

}
