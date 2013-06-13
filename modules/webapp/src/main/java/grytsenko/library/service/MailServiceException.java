package grytsenko.library.service;

/**
 * Thrown if an error occurred in {@link MailService}.
 */
public class MailServiceException extends Exception {

    private static final long serialVersionUID = 4994553876023539603L;

    /**
     * Creates an exception.
     */
    public MailServiceException() {
    }

    /**
     * Creates an exception.
     * 
     * @param message
     *            the description of error.
     */
    public MailServiceException(String message) {
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
    public MailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
