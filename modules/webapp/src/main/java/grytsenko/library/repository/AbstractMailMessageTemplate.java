/*
 * Copyright 2013 Dmitry Korotych &lt;dkorotych at gmail dot com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grytsenko.library.repository;

import grytsenko.library.model.book.BookDetails;
import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
public abstract class AbstractMailMessageTemplate implements MailMessageTemplateRepository.MailMessageTemplate {

    private final String subjectTemplate;
    private final String textTemplate;
    private final boolean important;

    /**
     * Creates a template.
     *
     * @param subjectTemplate the template for subject.
     * @param textTemplate the template for text.
     * @param emailForFeedback the email for feedback.
     * @param important the flag, which shows that mail is important.
     */
    public AbstractMailMessageTemplate(String subjectTemplate, String textTemplate, boolean important) {
        this.subjectTemplate = subjectTemplate;
        this.textTemplate = textTemplate;

        this.important = important;
    }

    /**
     * Composes a message about shared book.
     */
    @Override
    public SimpleMailMessage compose(SharedBook book, User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getMail());

        User manager = book.getManagedBy();
        if (important) {
            message.setCc(manager.getMail());
        }

        String subject = renderSubject(subjectTemplate);
        message.setSubject(subject);

        String text = renderText(textTemplate, book.getDetails(), user, manager);
        message.setText(text);

        return message;
    }

    protected abstract String renderSubject(String templateName);

    protected abstract String renderText(String templateName, BookDetails book, User user, User manager);
}
