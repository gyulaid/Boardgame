package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SignUpPageController extends Controller{

    PlayerService playerService = new PlayerService();


    @FXML
    private VBox SignUpPage;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    void onMainMenu(ActionEvent event) {

    }

    @FXML
    void onRegister(ActionEvent event) {
        logger.info("Register button pressed");
        playerService.createUser(userNameField.getText(), passwordField.getText());
        changeToScreen("/MainMenuUI.fxml", event);
    }

}
