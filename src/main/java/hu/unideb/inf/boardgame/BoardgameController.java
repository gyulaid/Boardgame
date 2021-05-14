package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class BoardgameController extends Controller {

    PlayerCache playerCache;


    @FXML
    private GridPane gameboard;

    @FXML
    private Label playerBlueLabel;

    @FXML
    private Label playerRedLabel;

    @FXML
    private void initialize() {
        playerBlueLabel.setText(PlayerCache.getPlayerInstance(PlayerColors.BLUE).getUserName());
        playerRedLabel.setText((PlayerCache.getPlayerInstance(PlayerColors.RED).getUserName()));

        for (int i = 0; i < gameboard.getRowCount() - 1; i++) {
            for (int j = 0; j < gameboard.getColumnCount(); j++) {
                StackPane cell = createCell(i, j);
                gameboard.add(cell, j, i);
            }
        }
    }

    @FXML
    void onExit(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }


    private StackPane createCell(int i, int j) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");
        return cell;
    }

}







