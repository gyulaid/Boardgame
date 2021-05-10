package hu.unideb.inf.boardgame.player;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private PlayerDao playerDao = new PlayerDao("players.json");
    private List<Player> players;

    public PlayerService(){

    }

    /**
     * Creates a new user and adds it to the database
     *
     * @param userName string representing the username of the user
     * @param password string representing the password of the user
     * @return whether the user creation was succesful or not
     */
    public void createUser(String userName, String password) {

        logger.info("Adding player to the database");
        players.add(new Player(userName, password));


    }








}
