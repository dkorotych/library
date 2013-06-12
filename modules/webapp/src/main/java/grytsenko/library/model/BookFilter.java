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
    AVAILABLE;

    /**
     * Returns the default filter.
     * 
     * @return
     */
    public static BookFilter getDefault() {
        return BookFilter.ALL;
    }

    /**
     * Parses the string and returns appropriate filter.
     * 
     * <p>
     * If the parsed string contains incorrect value, then returns a filter by
     * default.
     */
    public static BookFilter fromString(String filter) {
        try {
            return BookFilter.valueOf(filter);
        } catch (IllegalArgumentException exception) {
            return BookFilter.getDefault();
        }
    }

}
