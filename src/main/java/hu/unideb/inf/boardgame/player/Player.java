package hu.unideb.inf.boardgame.player;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for storing data of a player
 */
@NoArgsConstructor
@Data
public class Player {

    /**
     * Username of the player
     */
    private String userName;


    /**
     * Password of the player
     */
    private String password;

    /**
     * Amount of wins by the player
     */
    private int amountOfWins;

    /**
     * Amount of losses by the player
     */
    private int amountOfLosses;


    /**
     * Constructor for a new player.
     * Sets the amount of wins/losses to 0
     * Initializes a username and password pair
     *
     * @param userName Username of the player as a {@code String}
     * @param password Password of the player as a String
     */
    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
        amountOfWins = 0;
        amountOfLosses = 0;
    }


    /**
     * Calculates the amount of total games played, wins and losses together
     *
     * @return an integer number of total games played
     */
    private int gamesPlayed() {
        return amountOfLosses + amountOfWins;
    }


    /**
     * Calculates win per lose ratio
     *
     * @return a floating point number calculated from wins and losses
     */
    private double winPerLoseRatio() {
        return ((double) amountOfWins) / amountOfLosses;
    }


}
