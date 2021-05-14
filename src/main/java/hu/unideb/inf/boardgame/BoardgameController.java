package hu.unideb.inf.boardgame;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;


public class BoardgameController extends Controller{

    PlayerCache playerCache;


    @FXML
    private GridPane gameboard;

    @FXML
    private Label playerBlueLabel;

    @FXML
    private Label playerRedLabel;

    @FXML
    private void initialize(){
        playerBlueLabel.setText(PlayerCache.getPlayerInstance(PlayerColors.BLUE).getUserName());
        playerRedLabel.setText((PlayerCache.getPlayerInstance(PlayerColors.RED).getUserName()));
    }


    @FXML
    void onExit(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }



}







