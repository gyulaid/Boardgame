package hu.unideb.inf.boardgame.controllers;

import hu.unideb.inf.boardgame.gameresults.GameHistory;
import hu.unideb.inf.boardgame.gameresults.GameHistoryService;
import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;


/**
 * Controller class of the toplist UI.
 */
public class TopListController extends Controller {

    PlayerService playerService = new PlayerService();
    GameHistoryService gameHistoryService = new GameHistoryService();


    @FXML
    private GridPane toplist;


    @FXML
    void initialize() {
        addTopList();
    }


    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onMainMenu(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }

    @FXML
    void onHistory(ActionEvent event) {
        changeToScreen("History.fxml", event);
    }


    private void addTopList() {

        List<Player> topPlayers = playerService.getTopList();
        toplist.setStyle("-fx-grid-lines-visible: true; -fx-background-color: aliceblue; -fx-border-style: solid;");

        for (int i = 0; i < 10; i++) {

            if (i < playerService.getTopList().size()) {
                toplist.add(new Label(Integer.toString(i + 1)), 0, i + 1);
                toplist.add(new Label(topPlayers.get(i).getUserName()), 1, i + 1);
                toplist.add(new Label(topPlayers.get(i).gamesPlayed().toString()), 2, i + 1);
                toplist.add(new Label(topPlayers.get(i).winPerLoseRatioAsString()), 3, i + 1);
            }
        }

    }




}
