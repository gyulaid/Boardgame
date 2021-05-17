package hu.unideb.inf.boardgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Basic controller class.
 */
public class Controller {



    /**
     * Change to a another view.
     *
     * @param fxml  fxml filepath
     * @param event .
     */
    public void changeToScreen(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
            Logger.info("Changed screen");

        } catch (IOException ioe) {
            Logger.error("Error: Cannot load the FXML file" + fxml);
            ioe.printStackTrace();
        }

    }

    /**
     * Change to a another view.
     * This method is used to change screen after the last step.
     *
     * @param fxml  fxml filepath
     * @param event .
     */
    public void changeToScreen(String fxml, MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
            Logger.info("Changed screen");

        } catch (IOException ioe) {
            Logger.error("Error: Cannot load the FXML file" + fxml);
            ioe.printStackTrace();
        }

    }
}
