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

import com.google.common.base.Function;
import grytsenko.library.repository.AbstractMailMessageTemplateRepository;
import java.util.Locale;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@Repository
public class ThymeleafMailMessageRepository extends AbstractMailMessageTemplateRepository<ThymeleafMailMessageTemplate> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafMailMessageRepository.class);
    @Autowired
    private SpringTemplateEngine templateEngine;

    public ThymeleafMailMessageRepository() {
        super(new TemplateCreator());
    }

    @Override
    protected ThymeleafMailMessageTemplate get(String name) {
        ThymeleafMailMessageTemplate returnValue = super.get(name);
        returnValue.setTemplateEngine(templateEngine);
        return returnValue;
    }

    private static class TemplateCreator implements Function<String, ThymeleafMailMessageTemplate> {

        @Override
        public ThymeleafMailMessageTemplate apply(String eventName) {
            ThymeleafMailMessageTemplate returnValue = null;
            Validate.notNull(eventName, "The \"eventName\" is null");
            String name = eventName.toUpperCase(Locale.ENGLISH);
            try {
                Template template = Template.valueOf(name);
                returnValue = createTemplateMessage(template);
            } catch (IllegalArgumentException exception) {
                LOGGER.warn(exception.getLocalizedMessage(), exception);
            }
            return returnValue;
        }

        private ThymeleafMailMessageTemplate createTemplateMessage(Template template) {
            return new ThymeleafMailMessageTemplate(template.getSubjectTemplateName(), template.getTextTemplateName(), template.isImportant(), template.getEventType());
        }
    }

    private static enum Template {

        AVAILABLE,
        RESERVED,
        RELEASED,
        BORROWED,
        RETURNED;

        String getSubjectTemplateName() {
            return "book/subject.xhtml";
        }

        String getTextTemplateName() {
            return "book/" + getEventType() + ".xhtml";
        }

        boolean isImportant() {
            return this != AVAILABLE ? true : false;
        }

        String getEventType() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
