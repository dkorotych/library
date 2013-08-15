package grytsenko.library.repository.stringtemplate;

import grytsenko.library.model.book.BookDetails;
import grytsenko.library.model.user.User;
import grytsenko.library.repository.AbstractMailMessageTemplate;

import org.stringtemplate.v4.ST;

/**
 * Template for mail about a shared book.
 */
public class StringTemplateMailMessageTemplate extends AbstractMailMessageTemplate {

    private static final String BOOK_DETAILS_ATTR = "bookDetails";
    private static final String USER_ATTR = "user";
    private static final String MANAGER_ATTR = "manager";
    private ST subjectTemplate;
    private ST textTemplate;

    /**
     * Creates a template.
     *
     * @param subjectTemplate
     *            the template for subject.
     * @param textTemplate
     *            the template for text.
     * @param important
     *            the flag, which shows that mail is important.
     */
    public StringTemplateMailMessageTemplate(ST subjectTemplate, ST textTemplate,
            boolean important) {
        super(subjectTemplate.getName(), textTemplate.getName(), important);
        this.subjectTemplate = subjectTemplate;
        this.textTemplate = textTemplate;
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

    @Override
    protected String renderSubject(String templateName) {
        return renderSubject();
    }

    @Override
    protected String renderText(String templateName, BookDetails book, User user, User manager) {
        return renderText(book, user, manager);
    }
}
