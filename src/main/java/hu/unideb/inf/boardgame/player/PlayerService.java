package hu.unideb.inf.boardgame.player;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerService {


    private PlayerDao playerDao = new PlayerDao();

    /**
     * Creates a new user and adds it to the database
     *
     * @param userName string representing the username of the user
     * @param password string representing the password of the user
     * @return whether the user creation was succesful or not
     */
    public void createUser(String userName, String password) {

        log.info("Adding player to the database");

        Player playerToAdd = new Player(userName, password);
        playerDao.savePlayer(playerToAdd);

        log.info("Player added");
    }

    public boolean searchForUser(String userName){
        return playerDao.searchByUserName(userName) != null;
    }



}
