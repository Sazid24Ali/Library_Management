package com.lib.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class javafx_main extends Application{

    private ConfigurableApplicationContext context;

    @Override
    public void init(){
        this.context = SpringApplication.run(LibraryManagementApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Main.fxml"));
        loader.setControllerFactory(context::getBean);

        Parent root=loader.load();

        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();

    }

    @Override
    public void stop(){
        this.context.close();
    }
    
    
    
}
