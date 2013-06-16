package grytsenko.library.test;

import grytsenko.library.model.DsUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;

/**
 * Utilities for work with users in tests.
 */
public final class TestUsers {

    /**
     * The identifier for quest.
     */
    public static final Long GUEST_ID = 1L;
    /**
     * The user name for guest.
     */
    public static final String GUEST_NAME = "guest";
    /**
     * The mail for guest.
     */
    public static final String GUEST_MAIL = "guest@test.com";

    /**
     * The identifier for manager.
     */
    public static final Long MANAGER_ID = 5L;
    /**
     * The user name for manager.
     */
    public static final String MANAGER_NAME = "manager";

    /**
     * Creates a guest.
     */
    public static User guest() {
        User guest = new User();
        guest.setId(GUEST_ID);
        guest.setUsername(GUEST_NAME);
        guest.setRole(UserRole.USER);
        return guest;
    }

    /**
     * Data about guest from DS.
     */
    public static DsUser guestFromDs() {
        DsUser ldapGuest = new DsUser();
        ldapGuest.setUsername(GUEST_NAME);
        ldapGuest.setMail(GUEST_MAIL);
        return ldapGuest;
    }

    /**
     * Creates a manager.
     */
    public static User manager() {
        User manager = new User();
        manager.setId(MANAGER_ID);
        manager.setUsername(MANAGER_NAME);
        manager.setRole(UserRole.MANAGER);
        return manager;
    }

    private TestUsers() {
    }

}
