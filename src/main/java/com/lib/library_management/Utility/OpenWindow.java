package com.lib.library_management.Utility;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.lib.library_management.Controller.MainController;
import com.lib.library_management.Controller.issueController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class OpenWindow {

    @Autowired
    private ApplicationContext context;

    Stage initializeTheStage(FXMLLoader loader, Stage parent, String Title) throws Exception {
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        Stage stage = new Stage();

        // Change the Size here for the main screen
        double minWidth = root.minWidth(-1); // Passing -1 to get the computed value
        double minHeight = root.minHeight(-1); // Passing -1 to get the computed value

        // Set the minimum width and height for the stage
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);

        // stage.setMaxWidth(minWidth);
        // stage.setMaxHeight(minHeight);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parent);

        stage.setTitle(Title);
        stage.setScene(new Scene(root));
        return stage;

    }

    public void openScene(String FileName, String Title, Stage parent, MainController mainControllerObj) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + FileName + ".fxml"));
            Stage stage = initializeTheStage(loader, parent, Title);
            stage.setOnHiding(e -> mainControllerObj.setBooksCount());

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openScene(String FileName, String Title, Stage parent, String RollNo,
            MainController mainControllerObj) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + FileName + ".fxml"));
            Stage stage = initializeTheStage(loader, parent, Title);

            issueController controller = loader.getController();
            controller.setRollNo(RollNo);
            controller.setMainController(mainControllerObj);

            // Remove this comments if we want the issue book window size to be not fully
            // maximized
            // stage.setMaxWidth(650);
            // stage.setMaxHeight(350);
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

    public boolean openConfirmation(String title, String message) {
        // ButtonType Yes = new ButtonType("Yes",);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                message, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            return true;
        }

        return false;
    }
}
