package com.lib.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lib.library_management.Controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class javafx_main extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        this.context = SpringApplication.run(LibraryManagementApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Main.fxml"));
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();

        MainController controller = loader.getController();
        // Pass the primary stage to the controller
        controller.setPrimaryStage(primaryStage);

        // Change the Size here for the main screen
        double minWidth = root.minWidth(-1); // Passing -1 to get the computed value
        double minHeight = root.minHeight(-1); // Passing -1 to get the computed value

        // Set the minimum width and height for the stage
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);

        // primaryStage.setMaxWidth(minWidth);
        // primaryStage.setMaxHeight(minHeight);

        primaryStage.setTitle("Main Screen");
        primaryStage
                .setScene(new Scene(root));
        primaryStage.show();

    }

    @Override
    public void stop() {
        this.context.close();
    }

}
