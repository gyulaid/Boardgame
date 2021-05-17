package hu.unideb.inf.boardgame.gameresults;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.unideb.inf.boardgame.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for writing and reading the database.
 */
public class GameHistoryDao {


    private static Logger log = LoggerFactory.getLogger(GameHistoryDao.class);
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    File file;

    /**
     * Collects history to list from json file.
     *
     * @return List of GameHistory objects of the last games
     */
    public List<GameHistory> getHistory() {
        var ref = new TypeReference<List<GameHistory>>() {
        };
        String path = System.getProperty("user.home")+"/.gamedata";
        File dir = new File(path);
        file = new File(dir.getPath()+"/history.json");

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
     * Saves result of currently finished game.
     *
     * @param resultToSave GameHistory object of the currently finished game
     */
    public void saveResults(GameHistory resultToSave) {

        try {
            List<GameHistory> history = getHistory();

            history.add(resultToSave);

            FileWriter writer = new FileWriter(file);

            objectMapper.writeValue(writer, history);

            log.debug("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex.getMessage());
        }
    }


}
