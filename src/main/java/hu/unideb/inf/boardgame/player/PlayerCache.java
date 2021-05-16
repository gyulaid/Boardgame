package hu.unideb.inf.boardgame.player;

import lombok.extern.slf4j.Slf4j;

/**
 * Class for caching the logged in players.
 */
@Slf4j
public class PlayerCache {

    private static Player playerBlueInstance = null;
    private static Player playerRedInstance = null;

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
            log.info("Getting player blue data");
            return playerBlueInstance;
        } else if (color.equals(PlayerColors.RED)) {
            log.info("Getting player red data");
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
        log.info("Initializing player red in the cache");
        playerRedInstance = player;
    }


    /**
     * Initializes the instance of the blue player.
     *
     * @param player Player to be the blue instance
     */
    public static void initializePlayerBlue(Player player) {
        log.info("Initializing player blue in the cache");
        playerBlueInstance = player;
    }

}
