package grytsenko.library.service.book;

import grytsenko.library.model.book.OfferedBook;
import grytsenko.library.model.book.SearchResults;
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
    protected OfferedBooksRepository offeredBooksRepository;

    /**
     * Finds a book.
     */
    public OfferedBook findVoted(long bookId) {
        OfferedBook book = offeredBooksRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} was not found.", bookId);
            throw new BookNotFoundException();
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
