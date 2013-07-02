package grytsenko.library.model.user;

import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.guestFromDs;
import static grytsenko.library.test.Users.manager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grytsenko.library.model.user.DsUser;
import grytsenko.library.model.user.User;

import org.junit.Test;

public class UserTests {

    @Test
    public void testEquals() {
        assertTrue(guest().equals(guest()));
    }

    @Test
    public void testNotEquals() {
        assertFalse(manager().equals(guest()));

        assertFalse(manager().equals(new User()));
        assertFalse(new User().equals(guest()));

        assertFalse(manager().equals(null));
    }

    @Test
    public void testSyncWithDs() {
        DsUser dsUser = guestFromDs();

        User user = new User();
        user.syncWith(dsUser);

        assertEquals(dsUser.getFirstname(), user.getFirstname());
        assertEquals(dsUser.getLastname(), user.getLastname());
        assertEquals(dsUser.getMail(), user.getMail());
    }

}
