package hu.unideb.inf.boardgame.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PlayerDao {

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public List<Player> getPlayers() {
        var ref = new TypeReference<List<Player>>() {};
        try {
            return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("players.json"), ref);
        } catch (Exception e) {
            log.error("Error occured during reading file ");
        }
        return new ArrayList<>();
    }


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
    ;
    }



