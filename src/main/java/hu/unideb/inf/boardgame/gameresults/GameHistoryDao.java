package hu.unideb.inf.boardgame.gameresults;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.tinylog.Logger;

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
            Logger.error(e.getMessage());
        }

        if (file.exists()){
            try{
                return objectMapper.readValue(file, ref);
            } catch (Exception e){
                Logger.error("Error during reading file {}", e.getMessage());
            }
        } else {
            try{
                file.createNewFile();
            } catch (Exception e){
                Logger.error("Exception during creating file {}", e.getMessage());
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

            Logger.debug("Write file");

        } catch (Exception ex) {
            Logger.error("Exception caught {}", ex.getMessage());
        }
    }


}
