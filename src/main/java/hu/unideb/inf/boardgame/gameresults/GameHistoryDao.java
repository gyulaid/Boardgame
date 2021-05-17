package hu.unideb.inf.boardgame.gameresults;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class GameHistoryDao {


    private static Logger log = LoggerFactory.getLogger(GameHistoryDao.class);
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);




    public List<GameHistory> getHistory() {
        var ref = new TypeReference<List<GameHistory>>() {
        };
        try {
            return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("history.json"), ref);
        } catch (Exception e) {
            log.error("Error occured during reading file: {}", e.getMessage());
        }
        return new ArrayList<>();
    }


    public void saveResults(GameHistory resultToSave) {

        try {
            List<GameHistory> history = getHistory();

            history.add(resultToSave);

            FileWriter writer = new FileWriter(getClass().getClassLoader().getResource("history.json").getPath());

            objectMapper.writeValue(writer, history);

            log.debug("Write file");

        } catch (Exception ex) {
            log.error("Exception caught {}", ex.getMessage());
        }
    }



}
