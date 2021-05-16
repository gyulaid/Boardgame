package hu.unideb.inf.boardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Application class for the game.
 */
public class BoardgameApplication extends Application {


    /**
     * Starts the main screen of the application.
     *
     * @param stage .
     * @throws IOException if any I/O error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainMenuUI.fxml")));
        stage.setTitle("Boardgame");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}
