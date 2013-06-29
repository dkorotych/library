package grytsenko.library.service;

import static grytsenko.library.repository.BooksRepositoryUtils.save;
import static grytsenko.library.util.DateUtils.now;
import grytsenko.library.model.SharedBook;
import grytsenko.library.model.User;
import grytsenko.library.repository.SharedBooksRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manages shared books.
 */
@Service
public class ManageSharedBooksService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ManageSharedBooksService.class);

    @Autowired
    SharedBooksRepository sharedBooksRepository;

    /**
     * Reserves a book for user.
     */
    @Transactional
    public SharedBook reserve(SharedBook book, User user)
            throws BookNotUpdatedException {
        if (!book.canBeReserved()) {
            throw new BookNotUpdatedException("Book can not be reserved.");
        }
        LOGGER.debug("Reserved book {}.", book.getId());

        book.reserve(user, now());
        return save(book, sharedBooksRepository);
    }

    /**
     * Releases a book.
     */
    @Transactional
    public SharedBook release(SharedBook book, User user)
            throws BookNotUpdatedException {
        if (!book.canBeReleasedBy(user)) {
            throw new BookNotUpdatedException("Book can not be released.");
        }
        LOGGER.debug("Release book {}.", book.getId());

        book.release(user, now());
        return save(book, sharedBooksRepository);
    }

    /**
     * Takes out a book from library.
     */
    @Transactional
    public SharedBook takeOut(SharedBook book, User user)
            throws BookNotUpdatedException {
        if (!user.isManager()) {
            throw new BookNotUpdatedException("User has no permissions.");
        }
        if (!book.canBeTakenOutBy(user)) {
            throw new BookNotUpdatedException("Book can not be taken out.");
        }
        LOGGER.debug("Take out book {}.", book.getId());

        book.takeOut(user, now());
        return save(book, sharedBooksRepository);
    }

    /**
     * Takes back a book to library.
     */
    @Transactional
    public SharedBook takeBack(SharedBook book, User user)
            throws BookNotUpdatedException {
        if (!user.isManager()) {
            throw new BookNotUpdatedException("User has no permissions.");
        }
        if (!book.canBeTakenBackBy(user)) {
            throw new BookNotUpdatedException("Book can not be taken back.");
        }
        LOGGER.debug("Take back book {}.", book.getId());

        book.takeBack(user, now());
        return save(book, sharedBooksRepository);
    }

}
