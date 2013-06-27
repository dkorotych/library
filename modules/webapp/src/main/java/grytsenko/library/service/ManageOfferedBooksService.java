package grytsenko.library.service;

import grytsenko.library.model.OfferedBook;
import grytsenko.library.model.User;
import grytsenko.library.repository.OfferedBooksRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages offered books.
 */
@Service
public class ManageOfferedBooksService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ManageOfferedBooksService.class);

    @Autowired
    OfferedBooksRepository offeredBooksRepository;

    /**
     * Reserves a book for user.
     * 
     * @throws BookNotUpdatedException
     */
    @Transactional
    public OfferedBook addVote(OfferedBook book, User user)
            throws BookNotUpdatedException {
        if (book.hasVoteFrom(user)) {
            throw new BookNotUpdatedException("User can vote once.");
        }
        LOGGER.debug("Add vote from {} for book {}.", user.getUsername(),
                book.getId());
        book.addVote(user);

        return update(book);
    }

    private OfferedBook update(OfferedBook book) throws BookNotUpdatedException {
        try {
            return offeredBooksRepository.saveAndFlush(book);
        } catch (Exception exception) {
            LOGGER.warn("Can not save the book {}, because: '{}'.",
                    book.getId(), exception.getMessage());

            throw new BookNotUpdatedException("Can not save the book.");
        }
    }

}
