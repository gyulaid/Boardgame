package hu.unideb.inf.boardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Controller {

    public static final Logger logger = LoggerFactory.getLogger(Controller.class);


    public void changeToScreen(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();

        } catch (IOException ioe) {
            logger.error("Error: Cannot load the FXML file" + fxml);
            ioe.printStackTrace();
        }

    }
}
