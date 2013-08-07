package grytsenko.library.model.user;

/**
 * Possible roles of users.
 */
public enum UserRole {

    /**
     * The default role for all users.
     */
    USER,

    /**
     * The role for users, who manage books in library.
     */
    MANAGER;

    /**
     * The maximum length for names of constants in this enumeration.
     */
    public static final int LENGTH_MAX = 10;

}
