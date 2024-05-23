package com.lib.library_management.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert addingBookId != null : "fx:id=\"addingBookId\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert issueButton != null : "fx:id=\"issueButton\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert studentId != null : "fx:id=\"studentId\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert takenBookEdition != null : "fx:id=\"takenBookEdition\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert takenBookId != null : "fx:id=\"takenBookId\" was not injected: check your FXML file 'IssueController.fxml'.";
        assert takenBookTitle != null : "fx:id=\"takenBookTitle\" was not injected: check your FXML file 'IssueController.fxml'.";

    }

}



