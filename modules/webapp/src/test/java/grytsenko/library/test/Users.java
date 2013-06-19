package grytsenko.library.test;

import grytsenko.library.model.DsUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;

/**
 * Utilities for work with users in tests.
 */
public final class Users {

    public static final Long GUEST_ID = 1L;
    public static final String GUEST_NAME = "guest";
    public static final String GUEST_FIRST_NAME = "John";
    public static final String GUEST_LAST_NAME = "Doe";
    public static final String GUEST_MAIL = "john.doe@test.com";

    public static final Long MANAGER_ID = 5L;
    public static final String MANAGER_NAME = "manager";

    public static User guest() {
        User guest = new User();
        guest.setId(GUEST_ID);
        guest.setUsername(GUEST_NAME);
        guest.setRole(UserRole.USER);
        return guest;
    }

    public static DsUser guestFromDs() {
        DsUser guest = new DsUser();
        guest.setUsername(GUEST_NAME);
        guest.setFirstname(GUEST_FIRST_NAME);
        guest.setLastname(GUEST_LAST_NAME);
        guest.setMail(GUEST_MAIL);
        return guest;
    }

    public static User manager() {
        User manager = new User();
        manager.setId(MANAGER_ID);
        manager.setUsername(MANAGER_NAME);
        manager.setRole(UserRole.MANAGER);
        return manager;
    }

    private Users() {
    }

}
