package hu.unideb.inf.boardgame.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Player {

    private String userName;


    private String password;


    private int amountOfWins;


    private int amountOfLosses;



    public Player(String userName, String password){
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
    private int gamesPlayed(){
        return amountOfLosses + amountOfWins;
    }


    /**
     * Calculates win per lose ratio
     *
     * @return a floating point number calculated from wins and losses
     */
    private double winPerLoseRatio(){
        return ((double) amountOfWins) / amountOfLosses;
    }


}
