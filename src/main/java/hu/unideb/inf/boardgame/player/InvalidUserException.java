package hu.unideb.inf.boardgame.player;


/**
 * Custom exception class for user validation.
 */
public class InvalidUserException extends Exception {

    /**
     * Constructor with a message.
     *
     * @param message String of the message when the error occurs
     */
    public InvalidUserException(String message) {
        super(message);
    }
}
