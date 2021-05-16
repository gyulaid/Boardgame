package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller of the main menu view
 */
public class MainMenuController extends Controller {

    PlayerService playerService = new PlayerService();


    @FXML
    private Pane MainMenu;

    @FXML
    private TextField playerRed;

    @FXML
    private PasswordField playerRedPassword;

    @FXML
    private TextField playerBlue;

    @FXML
    private PasswordField playerBluePassword;



    @FXML
    void onInstructions(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("Boardgame instructions");
        alert.setContentText("This is a simple, chess-like boardgame\n"+
                            "If you have not registered yet,\n"+
                            "You need to, to play the game.\n"+
                            "After that you can choose a side, Blue or Red.\n"+
                            "\n"+
                            "Rules of the game:\n"+
                            "There are two restricted zones you can not step on.\n"+
                            "You can only step forward and diagonally.\n"+
                            "If you are willing to step diagonally, and there is\n"+
                            "an enemy Disk on the chosen square, \n"+
                            "then the enemy's disk will be removed from the table.\n"+
                            "The first player who can't make more moves loses.\n");
        alert.showAndWait();
    }

    @FXML
    void onQuitGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onStartGame(ActionEvent event) {
        try {
            if (playerService.validateLogIn(playerBlue.getText(), playerBluePassword.getText(), "Blue") &&
                    playerService.validateLogIn(playerRed.getText(), playerRedPassword.getText(), "Red")) {

                PlayerCache.initializePlayerBlue(playerService.getPlayerData(playerBlue.getText()));
                PlayerCache.initializePlayerRed(playerService.getPlayerData(playerRed.getText()));

                changeToScreen("BoardgameUI.fxml", event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void onTopList(ActionEvent event) {
        changeToScreen("TopList.fxml", event);

    }

    @FXML
    void onRegister(ActionEvent event) {
        changeToScreen("SignUpPage.fxml", event);
    }





}
