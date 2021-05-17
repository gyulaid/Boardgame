package hu.unideb.inf.boardgame.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.unideb.inf.boardgame.gameresults.GameResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Data access object to the database of users.
 */
public class PlayerDao {


    private static Logger log = LoggerFactory.getLogger(PlayerDao.class);
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private File file;

    /**
     * Gets all the users from the database.
     *
     * @return a list of player objects, may return an {@code ArrayList<>()}
     */
    public List<Player> getPlayers() {

        var ref = new TypeReference<List<Player>>() {
        };
        String path = System.getProperty("user.home")+"/.gamedata";
        File dir = new File(path);
        file = new File(dir.getPath()+"/players.json");

        try{
            Files.createDirectories(Paths.get(path));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (file.exists()){
            try{
                return objectMapper.readValue(file, ref);
            } catch (Exception e){
                log.error("Error during reading file {}", e.getMessage());
            }
        } else {
            try{
                file.createNewFile();
            } catch (Exception e){
                log.error("Exception during creating file {}", e.getMessage());
            }
        }

        return new ArrayList<>();
    }

    /**
     * Saves a player to the database.
     *
     * @param playerToSave a player object to save
     */
    public void savePlayer(Player playerToSave) {

        try {
            List<Player> players = getPlayers();

            players.add(playerToSave);

            FileWriter writer = new FileWriter(file);

            objectMapper.writeValue(writer, players);

            log.debug("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex.getMessage());
        }
    }

    /**
     * Deletes a player from the database.
     *
     * @param playerToDelete Player object to delete
     */
    public void deletePlayer(Player playerToDelete) {
        try {
            List<Player> players = getPlayers();
            log.info("Deleting player");
            players.remove(playerToDelete);

            FileWriter writer = new FileWriter(file);

            objectMapper.writeValue(writer, players);

            log.info("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex.getMessage());
        }
    }


    /**
     * Search in the database by username.
     *
     * @param userName String representing the username to find
     * @return {@code Player} object with the given username
     */
    public Player searchByUserName(String userName) {
        List<Player> playerList = getPlayers();
        for (Player player : playerList) {
            if (player.getUserName().equals(userName)) {
                return player;
            }
        }
        return null;
    }


    /**
     * Updates a player's win/lose data.
     *
     * @param userName String representing the username of the player
     * @param result   String representing if the player won or lost
     */
    public void updatePlayer(String userName, GameResult result) {
        try {
            List<Player> players = getPlayers();
            Player playerToUpdate = searchByUserName(userName);
            Player updatedPlayer = searchByUserName(userName);

            if (result.equals(GameResult.WON)) {
                updatedPlayer.setAmountOfWins(updatedPlayer.getAmountOfWins() + 1);
                log.info("Updateing player win");
            } else if (result.equals(GameResult.LOST)) {
                updatedPlayer.setAmountOfLosses(playerToUpdate.getAmountOfLosses() + 1);
                log.info("Updating player loss");
            }

            players.set(players.indexOf(playerToUpdate), updatedPlayer);

            FileWriter writer = new FileWriter(file);

            objectMapper.writeValue(writer, players);

            log.info("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex.getMessage());
        }
    }


}








