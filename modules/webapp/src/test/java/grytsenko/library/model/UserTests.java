package grytsenko.library.model;

import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.guestFromDs;
import static grytsenko.library.test.Users.manager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTests {

    @Test
    public void testIdentical() {
        assertTrue(guest().identicalTo(guest()));
    }

    @Test
    public void testNotIdentical() {
        assertFalse(manager().identicalTo(guest()));

        assertFalse(manager().identicalTo(new User()));
        assertFalse(new User().identicalTo(guest()));

        assertFalse(manager().identicalTo(null));
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
