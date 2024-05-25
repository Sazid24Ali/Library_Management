package com.lib.library_management.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class OpenWindow {

    @Autowired
    private ApplicationContext context;

    public void openScene(String FileName, String Title, Stage parent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + FileName + ".fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            Stage stage = new Stage();

            // Change the Size here for the main screen
            double minWidth = root.minWidth(-1); // Passing -1 to get the computed value
            double minHeight = root.minHeight(-1); // Passing -1 to get the computed value

            // Set the minimum width and height for the stage
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);

            stage.setMaxWidth(minWidth);
            stage.setMaxHeight(minHeight);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);

            stage.setTitle(Title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDialogue(String title, String dialogue) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(dialogue);

        // Show the dialog and wait for a response
        alert.showAndWait();

    }
}
