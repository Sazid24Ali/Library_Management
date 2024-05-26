package com.lib.library_management.Controller;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.events.Event;

import com.lib.library_management.Utility.utilityClass;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class issueController {

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

    public void setRollNo(String RollNo) {
        studentId.setText(RollNo);
        studentId.setEditable(false);
    }

    @FXML
    void initialize() {
        utilityClass.setIntegerLimiter(addingBookId, 7);

    }

    @FXML
    void addBook(MouseEvent event) {

    }

    @FXML
    void addBookToTable(MouseEvent event) {

    }

}
