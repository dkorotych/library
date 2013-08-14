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

import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
public interface MailMessageTemplateRepository<Type extends MailMessageTemplateRepository.MailMessageTemplate> {

    Type getBookAvailable();

    Type getBookReserved();

    Type getBookReleased();

    Type getBookBorrowed();

    Type getBookReturned();

    public interface MailMessageTemplate {

        void setEmailForFeedback(String emailForFeedback);

        SimpleMailMessage compose(SharedBook book, User user);
    }
}
