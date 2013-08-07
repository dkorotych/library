package grytsenko.library.model.book;

/**
 * Status of shared book.
 */
public enum SharedBookStatus {

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

    /**
     * The maximum length for names of constants in this enumeration.
     */
    public static final int LENGTH_MAX = 10;

}
