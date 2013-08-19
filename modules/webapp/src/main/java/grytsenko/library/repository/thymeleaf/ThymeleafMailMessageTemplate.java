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
package grytsenko.library.repository.thymeleaf;

import grytsenko.library.model.book.BookDetails;
import grytsenko.library.model.user.User;
import grytsenko.library.repository.AbstractMailMessageTemplate;
import java.util.Locale;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
public class ThymeleafMailMessageTemplate extends AbstractMailMessageTemplate {

    private static final Locale LOCALE = Locale.ENGLISH;
    private SpringTemplateEngine templateEngine;
    private String eventType;

    public ThymeleafMailMessageTemplate(String subjectTemplateName, String textTemplate, boolean important, String eventType) {
        super(subjectTemplateName, textTemplate, important);
        this.eventType = eventType;
    }

    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected String renderSubject(String templateName) {
        Context context = createContext();
        return templateEngine.process(templateName, context);
    }

    @Override
    protected String renderText(String templateName, BookDetails book, User user, User manager) {
        Context context = createContext();
        context.setVariable("book", book);
        context.setVariable("user", user);
        context.setVariable("manager", manager);
        return templateEngine.process(templateName, context);
    }

    private Context createContext() {
        Context context = new Context(LOCALE);
//        context.setVariable(SpelVariableExpressionEvaluator.THEMES_EVALUATION_VARIABLE_NAME, null);
        context.setVariable("eventType", eventType);
        return context;
    }
}
