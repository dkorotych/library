package grytsenko.library.service;

/**
 * Thrown if book was not updated.
 */
public class BookNotUpdatedException extends Exception {

    private static final long serialVersionUID = -6798114787417409556L;

    public BookNotUpdatedException() {
    }

    public BookNotUpdatedException(String message) {
        super(message);
    }

    public BookNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

}
