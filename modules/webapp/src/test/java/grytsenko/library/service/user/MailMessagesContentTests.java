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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailMessagesContentTestsConfiguration.class)
public class MailMessagesContentTests {

    private static final String LIBRARIAN_EMAIL = "librarian@test.com";
    private static final String READER_EMAIL = "reader@test.com";
    @Autowired
    private NotifyUsersService notifyUsersService;
    @Autowired
    private MailSender mailSender;
    private User reader;
    private SharedBook book;

    @Before
    public void setUp() {
        User librarian = User.create("librarian");
        librarian.setRole(UserRole.MANAGER);
        librarian.setMail(LIBRARIAN_EMAIL);
        reader = User.create("reader");
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
        executeNotifyTest(new BookAvailableContext());
    }

    @Test
    public void testBookBorrowedMessage() {
        executeNotifyTest(new BookBorrowedContext());
    }

    @Test
    public void testBookReservedMessage() {
        executeNotifyTest(new BookReservedContext());
    }

    @Test
    public void testBookReleasedMessage() {
        executeNotifyTest(new BookReleasedContext());
    }

    @Test
    public void testBookReturnedMessage() {
        executeNotifyTest(new BookReturnedContext());
    }

    private void executeNotifyTest(NotifyTestContext context) {
        context.executeNotify();
        SimpleMailMessage expectedMessage = createExceptedMessage(context.getSubject(), context.getText(), context.isAddCC());
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

    private interface NotifyTestContext {

        void executeNotify();

        String getSubject();

        String getText();

        boolean isAddCC();
    }

    private class BookAvailableContext implements NotifyTestContext {

        @Override
        public void executeNotify() {
            notifyUsersService.notifyAvailable(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book is available.";
        }

        @Override
        public String getText() {
            return "Hello reader.\n"
                    + "\n"
                    + "A book is available and you can reserve it.\n"
                    + "\n"
                    + "Book Details:\n"
                    + "    Title: Book Title\n"
                    + "    Authors: Author\n"
                    + "    Year: 2013\n"
                    + "\n"
                    + "If you received this mail by mistake, please, forward it to librarian@test.com with your comments.\n"
                    + "\n"
                    + "Thanks.";
        }

        @Override
        public boolean isAddCC() {
            return false;
        }
    }

    private class BookBorrowedContext implements NotifyTestContext {

        @Override
        public void executeNotify() {
            notifyUsersService.notifyBorrowed(book, reader);
        }

        @Override
        public String getSubject() {
            return "You've borrowed the book from library.";
        }

        @Override
        public String getText() {
            return "Hello reader.\n"
                    + "\n"
                    + "You've borrowed the book from library.\n"
                    + "Please remember to return it.\n"
                    + "\n"
                    + "Book Details:\n"
                    + "    Title: Book Title\n"
                    + "    Authors: Author\n"
                    + "    Year: 2013\n"
                    + "\n"
                    + "If you received this mail by mistake, please, forward it to librarian@test.com with your comments.\n"
                    + "\n"
                    + "Thanks.\n";
        }

        @Override
        public boolean isAddCC() {
            return true;
        }
    }

    private class BookReservedContext implements NotifyTestContext {

        @Override
        public void executeNotify() {
            notifyUsersService.notifyReserved(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book has been reserved for you.";
        }

        @Override
        public String getText() {
            return "Hello reader.\n"
                    + "\n"
                    + "Book has been reserved for you.\n"
                    + "You can borrow this book from a library.\n"
                    + "\n"
                    + "Book Details:\n"
                    + "    Title: Book Title\n"
                    + "    Authors: Author\n"
                    + "    Year: 2013\n"
                    + "\n"
                    + "If you received this mail by mistake, please, forward it to librarian@test.com with your comments.\n"
                    + "\n"
                    + "Thanks.";
        }

        @Override
        public boolean isAddCC() {
            return true;
        }
    }

    private class BookReleasedContext implements NotifyTestContext {

        @Override
        public void executeNotify() {
            notifyUsersService.notifyReleased(book, reader);
        }

        @Override
        public String getSubject() {
            return "Book is no longer reserved for you.";
        }

        @Override
        public String getText() {
            return "Hello reader.\n"
                    + "\n"
                    + "A book is no longer reserved for you.\n"
                    + "\n"
                    + "Book Details:\n"
                    + "    Title: Book Title\n"
                    + "    Authors: Author\n"
                    + "    Year: 2013\n"
                    + "\n"
                    + "If you received this mail by mistake, please, forward it to librarian@test.com with your comments.\n"
                    + "\n"
                    + "Thanks.";
        }

        @Override
        public boolean isAddCC() {
            return true;
        }
    }

    private class BookReturnedContext implements NotifyTestContext {

        @Override
        public void executeNotify() {
            notifyUsersService.notifyReturned(book, reader);
        }

        @Override
        public String getSubject() {
            return "You've returned a book to library.";
        }

        @Override
        public String getText() {
            return "Hello reader.\n"
                    + "\n"
                    + "You've returned a book to library.\n"
                    + "\n"
                    + "Book Details:\n"
                    + "    Title: Book Title\n"
                    + "    Authors: Author\n"
                    + "    Year: 2013\n"
                    + "\n"
                    + "If you received this mail by mistake, please, forward it to librarian@test.com with your comments.\n"
                    + "\n"
                    + "Thanks.\n";
        }

        @Override
        public boolean isAddCC() {
            return true;
        }
    }
}