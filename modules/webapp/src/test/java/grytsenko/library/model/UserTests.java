package grytsenko.library.model;

import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.manager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTests {

    @Test
    public void testIdentical() {
        assertTrue(guest().isIdenticalTo(guest()));
    }

    @Test
    public void testNotIdentical() {
        assertFalse(manager().isIdenticalTo(guest()));

        assertFalse(manager().isIdenticalTo(new User()));
        assertFalse(new User().isIdenticalTo(guest()));
    }

    @Test
    public void testSyncWithDs() {
        User user = new User();

        DsUser dsUser = new DsUser();
        dsUser.setFirstname("Anton");
        dsUser.setLastname("Grytsenko");
        dsUser.setMail("anthony.grytsenko@gmail.com");

        user.syncWith(dsUser);

        assertEquals(dsUser.getFirstname(), user.getFirstname());
        assertEquals(dsUser.getLastname(), user.getLastname());
        assertEquals(dsUser.getMail(), user.getMail());
    }

}