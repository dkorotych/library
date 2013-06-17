package grytsenko.library.service;

import grytsenko.library.model.Book;
import grytsenko.library.model.User;

import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Allows to send email notifications to users.
 */
@Service
public class NotifyUsersService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotifyUsersService.class);

    public static final String BOOK_RESERVED_SUBJECT = "mail.subject.reserved";
    public static final String BOOK_RESERVED_TEMPLATE = "notifyReserved";

    public static final String BOOK_RELEASED_SUBJECT = "mail.subject.released";
    public static final String BOOK_RELEASED_TEMPLATE = "notifyReleased";

    public static final String BOOK_BORROWED_SUBJECT = "mail.subject.borrowed";
    public static final String BOOK_BORROWED_TEMPLATE = "notifyBorrowed";

    public static final String BOOK_RETURNED_SUBJECT = "mail.subject.returned";
    public static final String BOOK_RETURNED_TEMPLATE = "notifyReturned";

    public static final String FEEDBACK_EMAIL = "mail.feedback";

    @Autowired
    MailSender mailSender;
    @Autowired
    Properties mailProperties;

    @Autowired
    MessageSource messageSource;

    /**
     * Sends notification that a book was reserved.
     */
    public void notifyReserved(Book book, User user)
            throws UserNotNotifiedException {
        LOGGER.debug("Sending notification that the book {} was reserved.",
                book.getId());

        notify(book, user, BOOK_RESERVED_SUBJECT, BOOK_RESERVED_TEMPLATE);
    }

    /**
     * Sends notification that book was released.
     */
    public void notifyReleased(Book book, User user)
            throws UserNotNotifiedException {
        LOGGER.debug("Sending notification that the book {} was released.",
                book.getId());

        notify(book, user, BOOK_RELEASED_SUBJECT, BOOK_RELEASED_TEMPLATE);
    }

    /**
     * Sends notification that book was borrowed.
     */
    public void notifyBorrowed(Book book, User user)
            throws UserNotNotifiedException {
        LOGGER.debug("Sending notification that the book {} was borrowed.",
                book.getId());

        notify(book, user, BOOK_BORROWED_SUBJECT, BOOK_BORROWED_TEMPLATE);
    }

    /**
     * Sends notification that book was returned.
     */
    public void notifyReturned(Book book, User user)
            throws UserNotNotifiedException {
        LOGGER.debug("Sending notification that the book {} was returned.",
                book.getId());

        notify(book, user, BOOK_RETURNED_SUBJECT, BOOK_RETURNED_TEMPLATE);
    }

    /**
     * Sends notification.
     */
    private void notify(Book book, User user, String subjectId,
            String templateId) throws UserNotNotifiedException {
        String from = mailProperties.getProperty(FEEDBACK_EMAIL);
        String to = user.getMail();
        String cc = book.getManagedBy().getMail();
        String subject = getSubject(subjectId);
        String text = getText(book, user, templateId);

        if (!StringUtils.hasLength(to)) {
            LOGGER.warn("Email of user {} is unknown.", user.getUsername());
            to = from;
        }
        if (!StringUtils.hasLength(cc)) {
            LOGGER.warn("Email of manager {} is unknown.", user.getUsername());
            cc = from;
        }

        SimpleMailMessage message = compose(from, to, cc, subject, text);

        LOGGER.debug("Email will be sent to {}.", to);
        LOGGER.debug("Copy of email will be sent to {}.", cc);

        try {
            mailSender.send(message);
        } catch (MailException exception) {
            LOGGER.warn("Can not send email for book {}, because: '{}'.",
                    book.getId(), exception.getMessage());

            throw new UserNotNotifiedException("Can not send email.");
        }
    }

    /**
     * Creates a subject for message.
     */
    private String getSubject(String subjectId) {
        return messageSource.getMessage(subjectId, null, Locale.getDefault());
    }

    /**
     * Creates a text for message.
     */
    private String getText(Book book, User user, String templateId) {
        STGroup templates = new STGroupDir("email", '$', '$');
        ST template = templates.getInstanceOf(templateId);

        template.add("book", book);
        template.add("user", user);
        template.add("manager", book.getManagedBy());

        return template.render();
    }

    /**
     * Composes simple message from its parts.
     */
    private SimpleMailMessage compose(String from, String to, String cc,
            String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);

        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
