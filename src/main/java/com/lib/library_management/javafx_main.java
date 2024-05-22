package com.lib.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

        // Change the Size here for the main screen
        double height = 540;
        double width = 744;

        primaryStage.setMinWidth(width);primaryStage.setMaxWidth(width);
        primaryStage.setMinHeight(height);primaryStage.setMaxHeight(height);
        

        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @Override
    public void stop() {
        this.context.close();
    }

}
