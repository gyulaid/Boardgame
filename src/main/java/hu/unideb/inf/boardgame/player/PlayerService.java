package hu.unideb.inf.boardgame.player;

import lombok.extern.slf4j.Slf4j;


/**
 * Service class to communicate with the Dao.
 * Provides service for multiple operations in the players database
 */
@Slf4j
public class PlayerService {


    /**
     * Creates connection to the database {@code PlayerDao}
     */
    private PlayerDao playerDao = new PlayerDao();


    /**
     * Creates a new user and adds it to the database
     *
     * @param userName string representing the username of the user
     * @param password string representing the password of the user
     */
    public void createUser(String userName, String password) {

        log.info("Adding player to the database");

        Player playerToAdd = new Player(userName, password);
        playerDao.savePlayer(playerToAdd);

        log.info("Player added");
    }


    /**
     * Searches a user in the database based on the username
     *
     * @param userName username of the searched player
     * @return a boolean true or false
     */
    public boolean searchForUser(String userName) {

        log.info("Searching for player in the database");

        return playerDao.searchByUserName(userName) != null;
    }


    /**
     * Checks if the given username and password is valid
     *
     * @param userName username of the user
     * @param password password of the user
     * @param color    a string representing the color of the player
     * @return boolean value, true if the login is succesful, false if the login failed
     * @throws InvalidLogInException if the validation fails
     */
    public boolean validateLogIn(String userName, String password, String color) throws InvalidLogInException {
        if (searchForUser(userName)) {
            if (playerDao.searchByUserName(userName).getPassword().equals(password)) {
                log.info("Login successful");
                return true;
            }
        }
        log.debug("Failed to login");
        throw new InvalidLogInException(color + " player has entered invalid username/password");
    }

}
