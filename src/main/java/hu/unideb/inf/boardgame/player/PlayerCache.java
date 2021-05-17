package hu.unideb.inf.boardgame.player;

import org.tinylog.Logger;

/**
 * Class for caching the logged in players.
 */
public class PlayerCache {

    private static Player playerBlueInstance = null;
    private static Player playerRedInstance = null;
    private static PlayerColors winningColor = null;

    private PlayerCache() {
    }


    /**
     * Returns an instance of a player.
     *
     * @param color PlayerColor value representing the color of the player
     * @return Player object of the given color from the current players
     */
    public static Player getPlayerInstance(PlayerColors color) {
        if (color.equals(PlayerColors.BLUE)) {
            Logger.info("Getting player blue data");
            return playerBlueInstance;
        } else if (color.equals(PlayerColors.RED)) {
            Logger.info("Getting player red data");
            return playerRedInstance;
        }
        return null;
    }

    /**
     * Initializes the instance of the red player.
     *
     * @param player Player to be the red instance
     */
    public static void initializePlayerRed(Player player) {
        Logger.info("Initializing player red in the cache");
        playerRedInstance = player;
    }


    /**
     * Initializes the instance of the blue player.
     *
     * @param player Player to be the blue instance
     */
    public static void initializePlayerBlue(Player player) {
        Logger.info("Initializing player blue in the cache");
        playerBlueInstance = player;
    }

    /**
     * Sets the winner color to store in Cache.
     *
     * @param color PlayerColors property of the winning player
     */
    public static void setWinningColor(PlayerColors color){
        winningColor = color;
    }

    /**
     * Getter for winning color.
     *
     * @return PlayerColors object
     */
    public static PlayerColors getWinningColor(){
        return winningColor;
    }

}
