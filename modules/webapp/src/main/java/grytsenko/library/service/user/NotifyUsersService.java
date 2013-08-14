package grytsenko.library.service.user;

import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;
import grytsenko.library.repository.MailMessageTemplateRepository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Sends notifications to users.
 */
@Service
public class NotifyUsersService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotifyUsersService.class);

    @Autowired
    protected MailSender mailSender;

    @Autowired
    private MailMessageTemplateRepository templateRepository;

    /**
     * Notifies users that book is available.
     */
    @Async
    public void notifyAvailable(SharedBook book, Collection<User> users) {
        for (User user : users) {
            notifyAvailable(book, user);
        }
    }

    /**
     * Notifies user that book is available.
     */
    @Async
    public void notifyAvailable(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} is available.",
                user.getUsername(), book.getId());

        notify(templateRepository.getBookAvailable(), book, user);
    }

    /**
     * Notifies user that book was reserved.
     */
    @Async
    public void notifyReserved(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was reserved.",
                user.getUsername(), book.getId());

        notify(templateRepository.getBookReserved(), book, user);
    }

    /**
     * Notifies user that book was released.
     */
    @Async
    public void notifyReleased(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was released.",
                user.getUsername(), book.getId());

        notify(templateRepository.getBookReleased(), book, user);
    }

    /**
     * Notifies user that book was borrowed.
     */
    @Async
    public void notifyBorrowed(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was borrowed.",
                user.getUsername(), book.getId());

        notify(templateRepository.getBookBorrowed(), book, user);
    }

    /**
     * Notifies user that book was returned.
     */
    @Async
    public void notifyReturned(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was returned.",
                user.getUsername(), book.getId());

        notify(templateRepository.getBookReturned(), book, user);
    }

    private void notify(MailMessageTemplateRepository.MailMessageTemplate template, SharedBook book, User user) {
        try {
            SimpleMailMessage message = template.compose(book, user);
            mailSender.send(message);
        } catch (Exception exception) {
            LOGGER.warn("Can not send email for book {}, because: '{}'.",
                    book.getId(), exception.getMessage());
        }
    }

}
