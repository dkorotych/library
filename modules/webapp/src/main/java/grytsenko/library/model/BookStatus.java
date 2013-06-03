package grytsenko.library.model;

/**
 * Possible statuses of the book.
 */
public enum BookStatus {

    /**
     * Book is available.
     */
    AVAILABLE,

    /**
     * Book is reserved by someone.
     */
    RESERVED,

    /**
     * Book is borrowed by someone.
     */
    BORROWED;

}
