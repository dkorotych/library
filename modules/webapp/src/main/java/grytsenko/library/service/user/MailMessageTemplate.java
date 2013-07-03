package grytsenko.library.service.user;

import grytsenko.library.model.book.BookDetails;
import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;

import org.springframework.mail.SimpleMailMessage;
import org.stringtemplate.v4.ST;

/**
 * Template for mail about a shared book.
 */
public class MailMessageTemplate {

    private static final String BOOK_DETAILS_ATTR = "bookDetails";
    private static final String USER_ATTR = "user";
    private static final String MANAGER_ATTR = "manager";

    private ST subjectTemplate;
    private ST textTemplate;

    private String emailForFeedback;

    private boolean important;

    /**
     * Creates a template.
     * 
     * @param subjectTemplate
     *            the template for subject.
     * @param textTemplate
     *            the template for text.
     * @param emailForFeedback
     *            the email for feedback.
     * @param important
     *            the flag, which shows that mail is important.
     */
    public MailMessageTemplate(ST subjectTemplate, ST textTemplate,
            String emailForFeedback, boolean important) {
        this.subjectTemplate = subjectTemplate;
        this.textTemplate = textTemplate;

        this.emailForFeedback = emailForFeedback;

        this.important = important;
    }

    /**
     * Composes a message about shared book.
     */
    public SimpleMailMessage compose(SharedBook book, User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailForFeedback);
        message.setTo(user.getMail());

        User manager = book.getManagedBy();
        if (important) {
            message.setCc(manager.getMail());
        }

        String subject = renderSubject();
        message.setSubject(subject);

        String text = renderText(book.getDetails(), user, manager);
        message.setText(text);

        return message;
    }

    private String renderSubject() {
        return subjectTemplate.render();
    }

    private String renderText(BookDetails book, User user, User manager) {
        textTemplate.remove(BOOK_DETAILS_ATTR);
        textTemplate.add(BOOK_DETAILS_ATTR, book);

        textTemplate.remove(USER_ATTR);
        textTemplate.add(USER_ATTR, user);

        textTemplate.remove(MANAGER_ATTR);
        textTemplate.add(MANAGER_ATTR, manager);

        return textTemplate.render();
    }

}
