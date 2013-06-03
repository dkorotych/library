package grytsenko.library.model;

/**
 * The supported filters for books.
 */
public enum BookFilter {

    /**
     * All books will be shown.
     */
    ALL,

    /**
     * Only books which are related to user (for example, which were reserved or
     * were borrowed by user).
     */
    RELATED,

    /**
     * Only books with status {@link BookStatus#AVAILABLE} will be shown.
     */
    AVAILABLE

}
