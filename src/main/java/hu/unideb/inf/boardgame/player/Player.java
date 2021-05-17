package hu.unideb.inf.boardgame.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tinylog.Logger;

import java.text.DecimalFormat;

/**
 * Class for storing data of a player.
 */
@JsonIgnoreProperties(value = {"color"})
@NoArgsConstructor
@Data
public class Player {


    /**
     * Username of the player.
     */
    private String userName;


    /**
     * Password of the player.
     */
    private String password;

    /**
     * Amount of wins by the player.
     */
    private Double amountOfWins;

    /**
     * Amount of losses by the player.
     */
    private Double amountOfLosses;

    /**
     * Color of the player in the current game.
     * Ignored by the DAO.
     */
    private PlayerColors color;

    /**
     * Constructor for a new player.
     * Sets the amount of wins/losses to 0.
     * Initializes a username and password pair.
     *
     * @param userName Username of the player as a {@code String}
     * @param password Password of the player as a String
     */
    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
        amountOfWins = 0.0;
        amountOfLosses = 0.0;
    }


    /**
     * Calculates the amount of total games played, wins and losses together.
     *
     * @return an integer number of total games played
     */
    public Double gamesPlayed() {
        Logger.info("Calculating total games played");
        return amountOfLosses + amountOfWins;
    }


    /**
     * Calculates win per lose ratio.
     *
     * @return a floating point number calculated from wins and losses
     */
    public Double winPerLoseRatio() {
        Logger.info("Calculating win per lose ratio");
        if (amountOfLosses == 0) {
            return amountOfWins;
        } else if (amountOfWins == 0) {
            return amountOfWins;
        }
        return amountOfWins / amountOfLosses;
    }


    /**
     * Formats the win per lose ratio to 3 decimal places.
     *
     * @return String form of the win per lose ratio
     */
    public String winPerLoseRatioAsString() {
        DecimalFormat df3 = new DecimalFormat("#.###");
        return df3.format(winPerLoseRatio());
    }

}
