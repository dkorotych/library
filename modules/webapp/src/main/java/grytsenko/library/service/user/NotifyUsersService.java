package grytsenko.library.service.user;

import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Sends notifications to users.
 */
@Service
public class NotifyUsersService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotifyUsersService.class);

    public static final String AVAILABLE_SUBJECT = "bookAvailableSubject";
    public static final String AVAILABLE_TEXT = "bookAvailableText";

    public static final String RESERVED_SUBJECT = "bookReservedSubject";
    public static final String RESERVED_TEXT = "bookReservedText";

    public static final String RELEASED_SUBJECT = "bookReleasedSubject";
    public static final String RELEASED_TEXT = "bookReleasedText";

    public static final String BORROWED_SUBJECT = "bookBorrowedSubject";
    public static final String BORROWED_TEXT = "bookBorrowedText";

    public static final String RETURNED_SUBJECT = "bookReturnedSubject";
    public static final String RETURNED_TEXT = "bookReturnedText";

    @Autowired
    MailSender mailSender;

    @Value("#{mailProperties['mail.feedback']}")
    String emailForFeedback;

    private STGroup templates;

    private SharedBookMailTemplate bookAvailable;
    private SharedBookMailTemplate bookReserved;
    private SharedBookMailTemplate bookReleased;
    private SharedBookMailTemplate bookBorrowed;
    private SharedBookMailTemplate bookReturned;

    @PostConstruct
    public void prepareTemplates() {
        templates = new STGroupFile("mail/mails.stg", '$', '$');

        bookAvailable = createMailTemplate(AVAILABLE_SUBJECT, AVAILABLE_TEXT,
                false);
        bookReserved = createMailTemplate(RESERVED_SUBJECT, RESERVED_TEXT, true);
        bookReleased = createMailTemplate(RELEASED_SUBJECT, RELEASED_TEXT, true);
        bookBorrowed = createMailTemplate(BORROWED_SUBJECT, BORROWED_TEXT, true);
        bookReturned = createMailTemplate(RETURNED_SUBJECT, RETURNED_TEXT, true);
    }

    private SharedBookMailTemplate createMailTemplate(
            String subjectTemplateName, String textTemplateName,
            boolean important) {
        ST subjectTemplate = templates.getInstanceOf(subjectTemplateName);
        ST textTemplate = templates.getInstanceOf(textTemplateName);
        return new SharedBookMailTemplate(subjectTemplate, textTemplate,
                emailForFeedback, important);
    }

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

        notify(bookAvailable, book, user);
    }

    /**
     * Notifies user that book was reserved.
     */
    @Async
    public void notifyReserved(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was reserved.",
                user.getUsername(), book.getId());

        notify(bookReserved, book, user);
    }

    /**
     * Notifies user that book was released.
     */
    @Async
    public void notifyReleased(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was released.",
                user.getUsername(), book.getId());

        notify(bookReleased, book, user);
    }

    /**
     * Notifies user that book was borrowed.
     */
    @Async
    public void notifyBorrowed(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was borrowed.",
                user.getUsername(), book.getId());

        notify(bookBorrowed, book, user);
    }

    /**
     * Notifies user that book was returned.
     */
    @Async
    public void notifyReturned(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was returned.",
                user.getUsername(), book.getId());

        notify(bookReturned, book, user);
    }

    private void notify(SharedBookMailTemplate template, SharedBook book,
            User user) {
        try {
            SimpleMailMessage message = template.compose(book, user);
            mailSender.send(message);
        } catch (Exception exception) {
            LOGGER.warn("Can not send email for book {}, because: '{}'.",
                    book.getId(), exception.getMessage());
        }
    }

}
