package grytsenko.library.model.book;

import static grytsenko.library.test.Books.offeredBook;
import static grytsenko.library.test.Users.guest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grytsenko.library.model.book.OfferedBook;
import grytsenko.library.model.user.User;

import org.junit.Test;

public class OfferedBookTests {

    @Test
    public void testAddVote() {
        OfferedBook book = offeredBook();
        User votedUser = guest();

        book.addVoter(votedUser);

        assertEquals(1, book.getVotersNum());
        assertTrue(book.hasVoter(votedUser));
    }

}
