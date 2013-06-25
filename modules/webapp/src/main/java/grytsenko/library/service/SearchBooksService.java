package grytsenko.library.service;

import grytsenko.library.model.Book;
import grytsenko.library.model.SearchResults;
import grytsenko.library.model.User;
import grytsenko.library.repository.BooksRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Searches books in library.
 */
@Service
public class SearchBooksService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchBooksService.class);

    @Autowired
    BooksRepository booksRepository;

    /**
     * Finds a book.
     */
    public Book find(long bookId) {
        Book book = booksRepository.findOne(bookId);

        if (book == null) {
            LOGGER.warn("Book {} was not found.", bookId);
            throw new BookNotFoundException();
        }

        return book;
    }

    /**
     * Finds all books.
     */
    public SearchResults<Book> findAll(int pageNum, int pageSize) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "The page number less than zero.");
        }

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Book> page = booksRepository.findAll(pageRequest);
        return toSearchResults(page);
    }

    private SearchResults<Book> toSearchResults(Page<Book> page) {
        SearchResults<Book> results = new SearchResults<>();
        results.setPageNum(page.getNumber());
        results.setPagesTotal(page.getTotalPages());
        results.setContent(page.getContent());
        return results;
    }

    /**
     * Finds books which are related to user.
     */
    public List<Book> findRelatedTo(User user) {
        return booksRepository.findByUsedBy(user);
    }

}
