package hu.unideb.inf.boardgame.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Data access object to the database of users
 */
@Slf4j
public class PlayerDao {

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Gets all the users from the database
     *
     * @return a list of player objects, may return an {@code ArrayList<>()}
     */
    public List<Player> getPlayers() {
        var ref = new TypeReference<List<Player>>() {
        };
        try {
            return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("players.json"), ref);
        } catch (Exception e) {
            log.error("Error occured during reading file ");
        }
        return new ArrayList<>();
    }

    /**
     * Saves a player to the database
     *
     * @param playerToSave a player object to save
     */
    public void savePlayer(Player playerToSave) {

        try {
            List<Player> players = getPlayers();

            players.add(playerToSave);

            FileWriter writer = new FileWriter(getClass().getClassLoader().getResource("players.json").getPath());

            objectMapper.writeValue(writer, players);

            log.info("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex);
            System.out.println("uzenet" + ex.getMessage());
        }
    }


    /**
     * Search in the database by username
     *
     * @param userName String representing the username to find
     * @return {@code Player} object with the given username
     */
    public Player searchByUserName(String userName) {
        List<Player> playerList = getPlayers();
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getUserName().equals(userName)) {
                return playerList.get(i);
            }
        }
        return null;
    }
}





