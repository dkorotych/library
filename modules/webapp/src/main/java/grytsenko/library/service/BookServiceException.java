package grytsenko.library.service;

/**
 * Thrown if an error occurred in {@link BookService}.
 */
public class BookServiceException extends Exception {

    private static final long serialVersionUID = -3550354936441100787L;

    /**
     * Creates an exception.
     */
    public BookServiceException() {
    }

    /**
     * Creates an exception.
     * 
     * @param message
     *            the description of error.
     */
    public BookServiceException(String message) {
        super(message);
    }

    /**
     * Creates an exception.
     * 
     * @param message
     *            the description of error.
     * @param cause
     *            the cause of error.
     */
    public BookServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
