package hu.unideb.inf.boardgame.controllers;

import hu.unideb.inf.boardgame.gameresults.GameHistory;
import hu.unideb.inf.boardgame.gameresults.GameHistoryService;
import hu.unideb.inf.boardgame.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.List;

/**
 * Controller class for the game history UI.
 */
public class HistoryUIController extends Controller{


    GameHistoryService gameHistoryService = new GameHistoryService();

    @FXML
    void initialize() {
        addHistory();
    }



    @FXML
    private GridPane lastGames;

    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onTopList(ActionEvent event) {
        changeToScreen("TopList.fxml", event);
    }

    private void addHistory(){
        List<GameHistory> gameHistory = gameHistoryService.getHistory();
        Collections.reverse(gameHistory);
        lastGames.setStyle("-fx-grid-lines-visible: true; -fx-background-color: aliceblue; -fx-border-style: solid;");

        for (int i = 0; i < 10; i++) {
            if (i < gameHistory.size()) {
                lastGames.add(new Label(gameHistory.get(i).getWinningPlayer()), 0, i + 1);
                lastGames.add(new Label(gameHistory.get(i).getLosingPlayer()), 1, i + 1);
                lastGames.add(new Label(gameHistory.get(i).getResult()), 2, i + 1);
            }
        }
    }


}
