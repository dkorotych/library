package grytsenko.library.test;

import grytsenko.library.model.LdapUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;

/**
 * Utilities for work with users in tests.
 */
public final class TestUsers {

    /**
     * The user name for guest.
     */
    public static final String GUEST_NAME = "guest";
    /**
     * The mail for guest.
     */
    public static final String GUEST_MAIL = "guest@test.com";

    /**
     * The user name for manager.
     */
    public static final String MANAGER_NAME = "manager";

    /**
     * Creates a guest.
     */
    public static User guest() {
        User guest = new User();
        guest.setUsername(GUEST_NAME);
        guest.setRole(UserRole.USER);
        return guest;
    }

    /**
     * Creates a LDAP user for guest.
     */
    public static LdapUser guestLdap() {
        LdapUser ldapGuest = new LdapUser();
        ldapGuest.setUsername(GUEST_NAME);
        ldapGuest.setMail(GUEST_MAIL);
        return ldapGuest;
    }

    /**
     * Creates a manager.
     */
    public static User manager() {
        User manager = new User();
        manager.setUsername(MANAGER_NAME);
        manager.setRole(UserRole.MANAGER);
        return manager;
    }

    private TestUsers() {
    }

}
