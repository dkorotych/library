package grytsenko.library.service.user;

/**
 * Thrown if user was not notified.
 */
public class UserNotNotifiedException extends Exception {

    private static final long serialVersionUID = 4994553876023539603L;

    public UserNotNotifiedException() {
    }

    public UserNotNotifiedException(String message) {
        super(message);
    }

    public UserNotNotifiedException(String message, Throwable cause) {
        super(message, cause);
    }

}
