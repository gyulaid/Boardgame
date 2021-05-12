package hu.unideb.inf.boardgame.player;


/**
 * Custom exception class for login validation
 */
public class InvalidLogInException extends Exception {
    public InvalidLogInException(String message) {
        super(message);
    }
}
