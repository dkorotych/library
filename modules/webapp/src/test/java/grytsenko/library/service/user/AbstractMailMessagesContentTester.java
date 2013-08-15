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

import grytsenko.library.model.book.BookDetails;
import grytsenko.library.model.book.SharedBook;
import grytsenko.library.model.user.User;
import grytsenko.library.model.user.UserRole;
import grytsenko.library.util.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractMailMessagesContentTester {

    protected static final String LIBRARIAN_EMAIL = "librarian@test.com";
    protected static final String READER_USERNAME = "reader";
    protected static final String READER_FIRST_NAME = "Ivan";
    protected static final String READER_EMAIL = "reader@test.com";
    @Autowired
    protected NotifyUsersService notifyUsersService;
    @Autowired
    protected MailSender mailSender;
    protected User reader;
    protected SharedBook book;
    protected BookAvailableContext bookAvailableContext;
    protected BookBorrowedContext bookBorrowedContext;
    protected BookReleasedContext bookReleasedContext;
    protected BookReservedContext bookReservedContext;
    protected BookReturnedContext bookReturnedContext;

    @Before
    public void setUp() {
        User librarian = User.create("librarian");
        librarian.setRole(UserRole.MANAGER);
        librarian.setMail(LIBRARIAN_EMAIL);
        reader = User.create(READER_USERNAME);
        reader.setMail(READER_EMAIL);
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle("Book Title");
        bookDetails.setAuthors("Author");
        bookDetails.setYear(2013);
        book = SharedBook.create(bookDetails, librarian, DateUtils.now());
    }

    @After
    public void tearDown() {
        reset(mailSender);
    }

    @Test
    public void testBookAvailableMessage() {
        executeNotifyTest(bookAvailableContext);
    }

    @Test
    public void testBookBorrowedMessage() {
        executeNotifyTest(bookBorrowedContext);
    }

    @Test
    public void testBookReleasedMessage() {
        executeNotifyTest(bookReleasedContext);
    }

    @Test
    public void testBookReservedMessage() {
        executeNotifyTest(bookReservedContext);
    }

    @Test
    public void testBookReturnedMessage() {
        executeNotifyTest(bookReturnedContext);
    }

    private void executeNotifyTest(NotifyTestContext context) {
        oneTestPass(context);
        reset(mailSender);
        reader.setFirstname(READER_FIRST_NAME);
        reader.setLastname("Ivanov");
        context.secondPass = true;
        oneTestPass(context);
    }

    private void oneTestPass(NotifyTestContext context) {
        context.executeNotify(notifyUsersService, book, reader);
        SimpleMailMessage expectedMessage = createExceptedMessage(context.getSubject(), context.getText(), context.isImportant());
        verify(mailSender).send(expectedMessage);
        verifyNoMoreInteractions(mailSender);
    }

    private SimpleMailMessage createExceptedMessage(String subject, String text, boolean addCC) {
        SimpleMailMessage returnValue = new SimpleMailMessage();
        returnValue.setFrom("anthony.grytsenko@gmail.com");
        returnValue.setTo(READER_EMAIL);
        if (addCC) {
            returnValue.setCc(LIBRARIAN_EMAIL);
        }
        returnValue.setSubject(subject);
        returnValue.setText(text);
        return returnValue;
    }

    protected static abstract class NotifyTestContext {

        private boolean secondPass = false;

        abstract void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader);

        abstract String getSubject();

        String getName() {
            return secondPass ? READER_FIRST_NAME : READER_USERNAME;
        }

        abstract String getText();

        boolean isImportant() {
            return true;
        }
    }

    protected static abstract class BookAvailableContext extends NotifyTestContext {

        @Override
        public void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader) {
            notifyUsersService.notifyAvailable(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book is available.";
        }

        @Override
        public boolean isImportant() {
            return false;
        }
    }

    protected static abstract class BookBorrowedContext extends NotifyTestContext {

        @Override
        public void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader) {
            notifyUsersService.notifyBorrowed(book, reader);
        }

        @Override
        public String getSubject() {
            return "You've borrowed the book from library.";
        }
    }

    protected static abstract class BookReleasedContext extends NotifyTestContext {

        @Override
        public void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader) {
            notifyUsersService.notifyReleased(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book is no longer reserved for you.";
        }
    }

    protected static abstract class BookReservedContext extends NotifyTestContext {

        @Override
        public void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader) {
            notifyUsersService.notifyReserved(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book has been reserved for you.";
        }
    }

    protected static abstract class BookReturnedContext extends NotifyTestContext {

        @Override
        public void executeNotify(NotifyUsersService notifyUsersService, SharedBook book, User reader) {
            notifyUsersService.notifyReturned(book, reader);
        }

        @Override
        public String getSubject() {
            return "You've returned a book to library.";
        }
    }
}
