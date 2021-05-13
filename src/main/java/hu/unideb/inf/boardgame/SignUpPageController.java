package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.player.InvalidUserException;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller of the sign up page view
 */
public class SignUpPageController extends Controller {

    PlayerService playerService = new PlayerService();


    @FXML
    private VBox SignUpPage;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    void onMainMenu(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }

    @FXML
    void onRegister(ActionEvent event) {
        if (!playerService.searchForUser(userNameField.getText())) {
            try {
                playerService.createUser(userNameField.getText(), passwordField.getText());
                changeToScreen("MainMenuUI.fxml", event);
            } catch (InvalidUserException iue) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occured during registration");
                alert.setContentText(iue.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occured during registration");
            alert.setContentText("Given username already in use, Try again!");
            alert.showAndWait();
        }

    }

}
