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
@ContextConfiguration(classes = ThymeleafMailMessageContentTestsConfiguration.class)
public class ThymeleafMailMessageContentTests extends AbstractMailMessagesContentTester {

    @Before
    @Override
    public void setUp() {
        super.setUp();
        bookAvailableContext = new BookAvailableContext() {
            @Override
            String getText() {
                return "<!DOCTYPE html>\n"
                        + "\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />\n"
                        + "        <title xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Book is available.</title>\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Hello " + getName() + ".</p>\n"
                        + "        <p>A book is available and you can reserve it.</p>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                <h4 xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book Title</h4>\n"
                        + "                <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Author</p>\n"
                        + "\n"
                        + "                <ul xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Publisher:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Year:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">2013</span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Language:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Thanks.</p>\n"
                        + "            <div style=\"font-size: small;\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                If you received this mail by mistake, please, forward it to&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"mailto:librarian@test.com\">librarian@test.com</a>&nbsp;with your comments.\n"
                        + "            </div>\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                Regards,<br xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" />\n"
                        + "                <em xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">The&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"https://github.com/grytsenko/library/contributors\">Library</a>&nbsp;Team</em>\n"
                        + "            </p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>\n";
            }
        };
        bookBorrowedContext = new BookBorrowedContext() {
            @Override
            String getText() {
                return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
                        + "\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"-//W3C//DTD XHTML 1.1//EN\">\n"
                        + "    <head xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />\n"
                        + "        <title xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">You&#39;ve borrowed the book from library.</title>\n"
                        + "    </head>\n"
                        + "    <body xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Hello " + getName() + ".</p>\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">You&#39;ve borrowed the book from library. Please remember to return it.</p>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                <h4 xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book Title</h4>\n"
                        + "                <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Author</p>\n"
                        + "\n"
                        + "                <ul xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Publisher:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Year:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">2013</span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Language:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Thanks.</p>\n"
                        + "            <div style=\"font-size: small;\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                If you received this mail by mistake, please, forward it to&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"mailto:librarian@test.com\">librarian@test.com</a>&nbsp;with your comments.\n"
                        + "            </div>\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                Regards,<br xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" />\n"
                        + "                <em xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">The&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"https://github.com/grytsenko/library/contributors\">Library</a>&nbsp;Team</em>\n"
                        + "            </p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>\n";
            }
        };
        bookReleasedContext = new BookReleasedContext() {
            @Override
            String getText() {
                return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
                        + "\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"-//W3C//DTD XHTML 1.1//EN\">\n"
                        + "    <head xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />\n"
                        + "        <title xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Book is no longer reserved for you.</title>\n"
                        + "    </head>\n"
                        + "    <body xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Hello " + getName() + ".</p>\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">A book is no longer reserved for you.</p>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                <h4 xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book Title</h4>\n"
                        + "                <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Author</p>\n"
                        + "\n"
                        + "                <ul xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Publisher:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Year:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">2013</span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Language:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Thanks.</p>\n"
                        + "            <div style=\"font-size: small;\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                If you received this mail by mistake, please, forward it to&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"mailto:librarian@test.com\">librarian@test.com</a>&nbsp;with your comments.\n"
                        + "            </div>\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                Regards,<br xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" />\n"
                        + "                <em xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">The&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"https://github.com/grytsenko/library/contributors\">Library</a>&nbsp;Team</em>\n"
                        + "            </p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>\n";
            }
        };
        bookReservedContext = new BookReservedContext() {
            @Override
            String getText() {
                return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
                        + "\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"-//W3C//DTD XHTML 1.1//EN\">\n"
                        + "    <head xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />\n"
                        + "        <title xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">Book has been reserved for you.</title>\n"
                        + "    </head>\n"
                        + "    <body xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Hello " + getName() + ".</p>\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book has been reserved for you. You can borrow this book from a library.</p>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                <h4 xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book Title</h4>\n"
                        + "                <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Author</p>\n"
                        + "\n"
                        + "                <ul xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Publisher:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Year:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">2013</span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Language:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Thanks.</p>\n"
                        + "            <div style=\"font-size: small;\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                If you received this mail by mistake, please, forward it to&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"mailto:librarian@test.com\">librarian@test.com</a>&nbsp;with your comments.\n"
                        + "            </div>\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                Regards,<br xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" />\n"
                        + "                <em xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">The&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"https://github.com/grytsenko/library/contributors\">Library</a>&nbsp;Team</em>\n"
                        + "            </p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>\n";
            }
        };
        bookReturnedContext = new BookReturnedContext() {
            @Override
            String getText() {
                return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
                        + "\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"-//W3C//DTD XHTML 1.1//EN\">\n"
                        + "    <head xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />\n"
                        + "        <title xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">You&#39;ve returned a book to library.</title>\n"
                        + "    </head>\n"
                        + "    <body xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Hello " + getName() + ".</p>\n"
                        + "        <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">You&#39;ve returned a book to library.</p>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                <h4 xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Book Title</h4>\n"
                        + "                <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Author</p>\n"
                        + "\n"
                        + "                <ul xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Publisher:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Year:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">2013</span>\n"
                        + "                    </li>\n"
                        + "                    <li xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                        <strong xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Language:</strong>\n"
                        + "                        <span xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\"></span>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">Thanks.</p>\n"
                        + "            <div style=\"font-size: small;\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                If you received this mail by mistake, please, forward it to&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"mailto:librarian@test.com\">librarian@test.com</a>&nbsp;with your comments.\n"
                        + "            </div>\n"
                        + "            <p xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">\n"
                        + "                Regards,<br xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" />\n"
                        + "                <em xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\">The&nbsp;<a xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xml:space=\"preserve\" shape=\"rect\" href=\"https://github.com/grytsenko/library/contributors\">Library</a>&nbsp;Team</em>\n"
                        + "            </p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>\n";
            }
        };
    }
}
