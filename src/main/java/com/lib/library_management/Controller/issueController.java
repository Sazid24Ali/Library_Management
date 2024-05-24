package com.lib.library_management.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

@Component
public class issueController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField addingBookId;

    @FXML
    private Button issueButton;

    @FXML
    private TextField studentId;

    @FXML
    private TableColumn<?, ?> takenBookEdition;

    @FXML
    private TableColumn<?, ?> takenBookId;

    @FXML
    private TableColumn<?, ?> takenBookTitle;

    @FXML
    void addBook(MouseEvent event) {

    }

    @FXML
    void addBookToTable(MouseEvent event) {

    }

    

}



