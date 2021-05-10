package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class MainMenuController extends Controller{

    PlayerService playerService = new PlayerService();



    @FXML
    private Pane MainMenu;

    @FXML
    private TextField playerRed;

    @FXML
    private TextField playerBlue;

    public MainMenuController() {
    }

    @FXML
    void onInstructions(ActionEvent event) {

    }

    @FXML
    void onQuitGame(ActionEvent event) {

    }

    @FXML
    void onStartGame(ActionEvent event)  {


    }

    @FXML
    void onTopList(ActionEvent event) {

    }

    @FXML
    void onRegister(ActionEvent event){
        changeToScreen("SignUpPage.fxml", event);
    }



}
