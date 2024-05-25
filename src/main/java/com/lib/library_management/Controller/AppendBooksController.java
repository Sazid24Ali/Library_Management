package com.lib.library_management.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppendBooksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bookCode;

    @FXML
    private TextArea disclaimer;

    @FXML
    private TextField inputOfBookIds;

    @FXML
    private Button saveButtonForScene;

    @FXML
    private Button searchWithBookCode;

    @FXML
    private TableView<?> tableToShowBook;

    @FXML
    void initialize() {
        assert bookCode != null : "fx:id=\"bookCode\" was not injected: check your FXML file 'AppendBooks.fxml'.";
        assert disclaimer != null : "fx:id=\"disclaimer\" was not injected: check your FXML file 'AppendBooks.fxml'.";
        assert inputOfBookIds != null : "fx:id=\"inputOfBookIds\" was not injected: check your FXML file 'AppendBooks.fxml'.";
        assert saveButtonForScene != null : "fx:id=\"saveButtonForScene\" was not injected: check your FXML file 'AppendBooks.fxml'.";
        assert searchWithBookCode != null : "fx:id=\"searchWithBookCode\" was not injected: check your FXML file 'AppendBooks.fxml'.";
        assert tableToShowBook != null : "fx:id=\"tableToShowBook\" was not injected: check your FXML file 'AppendBooks.fxml'.";

    }

}

