package hu.unideb.inf.boardgame.player;

import hu.unideb.inf.boardgame.gameresults.GameResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class to communicate with the Dao.
 * Provides service for multiple operations in the players database
 */
public class PlayerService {

    private static Logger log = LoggerFactory.getLogger(PlayerService.class);

    /**
     * Creates connection to the database {@code PlayerDao}.
     */
    private PlayerDao playerDao = new PlayerDao();


    /**
     * Makes a list of every single player in the database.
     *
     * @return List of Player objects from the database
     */
    public List<Player> getAllPlayers() {
        return playerDao.getPlayers();
    }

    /**
     * Creates a new user and adds it to the database.
     *
     * @param userName string representing the username of the user
     * @param password string representing the password of the user
     */
    public void createUser(String userName, String password) throws InvalidUserException {

        if (userName.length() < 5 || userName.length() > 30) {
            throw new InvalidUserException("Username must be between 5 and 30 characters");
        } else if (password.length() < 5 || password.contains(" ")) {
            throw new InvalidUserException("Password must be atleast 5 characters and can not contain spaces");
        }

        log.info("Adding player to the database");

        Player playerToAdd = new Player(userName, password);
        playerDao.savePlayer(playerToAdd);

        log.info("Player added");
    }


 /*   /**
     * Searches a user in the database based on the username.
     *
     * @param userName username of the searched player
     * @return a boolean true or false
     * /
    public boolean searchForUser(String userName) {

        log.info("Searching for player in the database");

        return playerDao.searchByUserName(userName) != null;
    }
*/

    /**
     * Returns the user's data.
     *
     * @param userName String as the username of the player
     * @return Player object with the matching username
     */
    public Player getPlayerData(String userName) {
        log.info("Getting player from database");
        return playerDao.searchByUserName(userName);
    }


    /**
     * Checks if the given username and password is valid.
     *
     * @param userName username of the user
     * @param password password of the user
     * @param color    a string representing the color of the player
     * @return boolean value, true if the login is succesful, false if the login failed
     * @throws InvalidUserException if the validation fails
     */
    public boolean validateLogIn(String userName, String password, String color) throws InvalidUserException {
        if (getPlayerData(userName) != null) {
            if (playerDao.searchByUserName(userName).getPassword().equals(password)) {
                log.info("Login successful");
                return true;
            }
        }
        log.debug("Failed to login");
        throw new InvalidUserException(color + " player has entered invalid username/password");
    }

    /**
     * Getter for the toplist ordered by the Win/Lose ratio of the players.
     *
     * @return List of players sorted
     */
    public List<Player> getTopList() {
        log.info("Creating top list;");
        return playerDao.getPlayers()
                .stream()
                .sorted(Comparator.comparing(Player::winPerLoseRatio).reversed())
                .collect(Collectors.toList());
    }


    /**
     * Updates a player's win/lose data.
     *
     * @param userName String representing the username of the player
     * @param result   String representing the outcome of the game of the player
     */
    public void updatePlayers(String userName, GameResult result) {
        log.info("Updateing player data");
        playerDao.updatePlayer(userName, result);
    }


}
