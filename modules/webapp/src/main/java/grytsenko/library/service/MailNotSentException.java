package grytsenko.library.service;

/**
 * Thrown if mail was not sent.
 */
public class MailNotSentException extends Exception {

    private static final long serialVersionUID = 4994553876023539603L;

    public MailNotSentException() {
    }

    public MailNotSentException(String message) {
        super(message);
    }

    public MailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

}
