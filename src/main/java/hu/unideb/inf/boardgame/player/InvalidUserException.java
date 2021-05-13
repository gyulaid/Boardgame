package hu.unideb.inf.boardgame.player;


/**
 * Custom exception class for user validation
 */
public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
