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

        book.addVote(votedUser);

        assertEquals(1, book.getVotesNum());
        assertTrue(book.hasVoteFrom(votedUser));
    }

    @Test(expected = IllegalStateException.class)
    public void testAlreadyVoted() {
        OfferedBook book = offeredBook();
        User votedUser = guest();

        book.addVote(votedUser);

        book.addVote(votedUser);
    }

}
