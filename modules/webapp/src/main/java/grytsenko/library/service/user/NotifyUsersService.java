package grytsenko.library.service.user;

import static grytsenko.library.util.StringUtils.isNullOrEmpty;
import grytsenko.library.model.SharedBook;
import grytsenko.library.model.User;

import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Sends notifications to users.
 */
@Service
public class NotifyUsersService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotifyUsersService.class);

    public static final String AVAILABLE_SUBJECT = "mail.subject.available";
    public static final String AVAILABLE_TEMPLATE = "notifyAvailable";

    public static final String RESERVED_SUBJECT = "mail.subject.reserved";
    public static final String RESERVED_TEMPLATE = "notifyReserved";

    public static final String RELEASED_SUBJECT = "mail.subject.released";
    public static final String RELEASED_TEMPLATE = "notifyReleased";

    public static final String BORROWED_SUBJECT = "mail.subject.borrowed";
    public static final String BORROWED_TEMPLATE = "notifyBorrowed";

    public static final String RETURNED_SUBJECT = "mail.subject.returned";
    public static final String RETURNED_TEMPLATE = "notifyReturned";

    public static final String FEEDBACK_EMAIL = "mail.feedback";

    @Autowired
    MailSender mailSender;
    @Autowired
    Properties mailProperties;

    @Autowired
    MessageSource messageSource;

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

        try {
            notify(book, user, AVAILABLE_SUBJECT, AVAILABLE_TEMPLATE, false);
        } catch (UserNotNotifiedException exception) {
            LOGGER.warn("User {} was not notified, because: '{}'.",
                    user.getUsername(), exception.getMessage());
        }
    }

    /**
     * Notifies user that book was reserved.
     */
    @Async
    public void notifyReserved(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was reserved.",
                user.getUsername(), book.getId());

        try {
            notify(book, user, RESERVED_SUBJECT, RESERVED_TEMPLATE, true);
        } catch (UserNotNotifiedException exception) {
            LOGGER.warn("User {} was not notified, because: '{}'.",
                    user.getUsername(), exception.getMessage());
        }
    }

    /**
     * Notifies user that book was released.
     */
    @Async
    public void notifyReleased(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was released.",
                user.getUsername(), book.getId());

        try {
            notify(book, user, RELEASED_SUBJECT, RELEASED_TEMPLATE, true);
        } catch (UserNotNotifiedException exception) {
            LOGGER.warn("User {} was not notified, because: '{}'.",
                    user.getUsername(), exception.getMessage());
        }
    }

    /**
     * Notifies user that book was borrowed.
     */
    @Async
    public void notifyBorrowed(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was borrowed.",
                user.getUsername(), book.getId());

        try {
            notify(book, user, BORROWED_SUBJECT, BORROWED_TEMPLATE, true);
        } catch (UserNotNotifiedException exception) {
            LOGGER.warn("User {} was not notified, because: '{}'.",
                    user.getUsername(), exception.getMessage());
        }
    }

    /**
     * Notifies user that book was returned.
     */
    @Async
    public void notifyReturned(SharedBook book, User user) {
        LOGGER.debug("Notify {} that the book {} was returned.",
                user.getUsername(), book.getId());

        try {
            notify(book, user, RETURNED_SUBJECT, RETURNED_TEMPLATE, true);
        } catch (UserNotNotifiedException exception) {
            LOGGER.warn("User {} was not notified, because: '{}'.",
                    user.getUsername(), exception.getMessage());
        }
    }

    /**
     * Sends notification.
     */
    private void notify(SharedBook book, User user, String subjectId,
            String templateId, boolean important)
            throws UserNotNotifiedException {
        String from = mailProperties.getProperty(FEEDBACK_EMAIL);

        String to = user.getMail();
        if (isNullOrEmpty(to)) {
            throw new UserNotNotifiedException("Email of user is not defined.");
        }
        LOGGER.debug("Email will be sent to {}.", to);

        String cc = null;
        if (important) {
            cc = book.getManagedBy().getMail();
            if (isNullOrEmpty(cc)) {
                throw new UserNotNotifiedException(
                        "Email of manager is not defined.");
            }
            LOGGER.debug("Copy of email will be sent to {}.", cc);
        }

        String subject = getSubject(subjectId);
        String text = getText(book, user, templateId);

        SimpleMailMessage message = compose(from, to, cc, subject, text);

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
    private String getText(SharedBook book, User user, String templateId) {
        STGroup templates = new STGroupDir("email", '$', '$');
        ST template = templates.getInstanceOf(templateId);

        template.add("book", book);
        template.add("user", user);
        template.add("manager", book.getManagedBy());

        return template.render();
    }

    /**
     * Composes message from its parts.
     */
    private SimpleMailMessage compose(String from, String to, String cc,
            String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        if (cc != null) {
            message.setCc(cc);
        }

        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
