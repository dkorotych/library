package grytsenko.library.model;

import static grytsenko.library.test.Books.offeredBook;
import static grytsenko.library.test.Users.guest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
