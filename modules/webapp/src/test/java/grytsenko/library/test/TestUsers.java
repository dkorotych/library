package grytsenko.library.test;

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
     * The user name for manager.
     */
    public static final String MANAGER_NAME = "manager";

    /**
     * Creates a guest.
     * 
     * @return created user.
     */
    public static User guest() {
        User guest = new User();
        guest.setName(GUEST_NAME);
        guest.setRole(UserRole.USER);
        return guest;
    }

    /**
     * Creates a manager.
     * 
     * @return created user.
     */
    public static User manager() {
        User manager = new User();
        manager.setName(MANAGER_NAME);
        manager.setRole(UserRole.MANAGER);
        return manager;
    }

    private TestUsers() {
    }

}
