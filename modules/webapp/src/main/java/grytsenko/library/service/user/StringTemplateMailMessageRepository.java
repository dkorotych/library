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
package grytsenko.library.service.user;

import com.google.common.base.Function;
import grytsenko.library.repository.AbstractMailMessageTemplateRepository;
import java.util.Locale;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@Repository
public class StringTemplateMailMessageRepository extends AbstractMailMessageTemplateRepository<StringTemplateMailMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTemplateMailMessageRepository.class);

    public StringTemplateMailMessageRepository() {
        super(new TemplateCreator());
    }

    private static class TemplateCreator implements Function<String, StringTemplateMailMessage> {

        private final STGroup templates;

        private TemplateCreator() {
            templates = new STGroupFile("mail/mails.stg", '$', '$');
        }

        @Override
        public StringTemplateMailMessage apply(String eventName) {
            StringTemplateMailMessage returnValue = null;
            Validate.notNull(eventName, "The \"eventName\" is null");
            String name = eventName.toUpperCase(Locale.ENGLISH);
            try {
                Template template = Template.valueOf(name);
                returnValue = createTemplateMessage(template.getSubjectTemplateName(), template.getTextTemplateName(), template.isImportant());
            } catch (IllegalArgumentException exception) {
                LOGGER.warn(exception.getLocalizedMessage(), exception);
            }
            return returnValue;
        }

        private StringTemplateMailMessage createTemplateMessage(String subjectTemplateName,
                String textTemplateName, boolean important) {
            ST subjectTemplate = templates.getInstanceOf(subjectTemplateName);
            ST textTemplate = templates.getInstanceOf(textTemplateName);
            return new StringTemplateMailMessage(subjectTemplate, textTemplate, important);
        }

        private static enum Template {

            AVAILABLE,
            RESERVED,
            RELEASED,
            BORROWED,
            RETURNED;

            String getSubjectTemplateName() {
                return "book" + getTemplateNamePart() + "Subject";
            }

            String getTextTemplateName() {
                return "book" + getTemplateNamePart() + "Text";
            }

            boolean isImportant() {
                return this != AVAILABLE ? true : false;
            }

            private String getTemplateNamePart() {
                return Character.toString(name().charAt(0)) + name().substring(1).toLowerCase(Locale.ENGLISH);
            }
        }
    }
}
