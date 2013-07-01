package grytsenko.library.service.book;

/**
 * Thrown if book was not found.
 */
public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7437494766998594233L;

    public BookNotFoundException() {
    }

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
