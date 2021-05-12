package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("Boardgame instructions");
        alert.setContentText("""
                This is a simple, chess-like boardgame
                If you have not registered yet,
                You need to, to play the game.
                After that you can choose a side, Blue or Red.
                
                Rules of the game:
                There are two restricted zones you can not step on.
                You can only step forward and diagonally.
                If you are willing to step diagonally, and there is
                an enemy Disk on the chosen square, 
                then the enemy's disk will be removed from the table.
                The first player who can't make more moves loses.
                """);
        alert.showAndWait();
    }

    @FXML
    void onQuitGame(ActionEvent event) {
        System.exit(0);
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
