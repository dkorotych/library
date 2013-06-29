package grytsenko.library.view;

import java.text.MessageFormat;

/**
 * Constants for requests mapping.
 */
public class Navigation {

    public static final String LOGIN_PATH = "/login";
    public static final String SEARCH_PATH = "/search";
    public static final String VOTE_PATH = "/vote";
    public static final String USER_PATH = "/user";
    public static final String SHARED_BOOK_PATH = "/shared-book";
    public static final String OFFERED_BOOK_PATH = "/offered-book";

    public static final String BOOK_ID_PARAM = "bookId";

    public static final String USER_ATTR = "user";

    public static String redirectToSearch() {
        return "redirect:" + SEARCH_PATH;
    }

    public static String redirectToVote() {
        return "redirect:" + VOTE_PATH;
    }

    public static String redirectToSharedBook(Long bookId) {
        return MessageFormat.format("redirect:{0}?{1}={2}", SHARED_BOOK_PATH,
                BOOK_ID_PARAM, bookId);
    }

    public static String redirectToOfferedBook(Long bookId) {
        return MessageFormat.format("redirect:{0}?{1}={2}", OFFERED_BOOK_PATH,
                BOOK_ID_PARAM, bookId);
    }

    private Navigation() {
    }

}
