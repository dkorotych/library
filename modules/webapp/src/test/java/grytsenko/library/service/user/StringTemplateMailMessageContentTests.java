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

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Dmitry Korotych &lt;dkorotych at gmail dot com&gt;
 */
@ContextConfiguration(classes = StringTemplateMailMessageContentTestsConfiguration.class)
public class StringTemplateMailMessageContentTests extends AbstractMailMessagesContentTester {

    @Before
    @Override
    public void setUp() {
        super.setUp();
        bookAvailableContext = new BookAvailableContext() {
            @Override
            public String getText() {
                return "Hello " + getName() + ".\n"
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
        };
        bookBorrowedContext = new BookBorrowedContext() {
            @Override
            public String getText() {
                return "Hello " + getName() + ".\n"
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
        };
        bookReleasedContext = new BookReleasedContext() {
            @Override
            public String getText() {
                return "Hello " + getName() + ".\n"
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
        };
        bookReservedContext = new BookReservedContext() {
            @Override
            public String getText() {
                return "Hello " + getName() + ".\n"
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
        };
        bookReturnedContext = new AbstractMailMessagesContentTester.BookReturnedContext() {
            @Override
            public String getText() {
                return "Hello " + getName() + ".\n"
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
        };
    }
}