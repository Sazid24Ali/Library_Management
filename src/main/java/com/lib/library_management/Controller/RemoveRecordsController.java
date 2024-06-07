package com.lib.library_management.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class RemoveRecordsController {

    @FXML
    private TableColumn<?, ?> colBookcode;

    @FXML
    private TableColumn<?, ?> colauthor;

    @FXML
    private TableColumn<?, ?> colbookname;

    @FXML
    private TableColumn<?, ?> coledition;

    @FXML
    private TableColumn<?, ?> colsubject;

    @FXML
    private RadioButton rdBookcode;

    @FXML
    private RadioButton rdBookid;

    @FXML
    private Button remove;

    @FXML
    private Button searchbtn;

    @FXML
    private TextField tfid_bookcode;

    @FXML
    void onclickcontentsvisible(ActionEvent event) {

    }

    @FXML
    void removebooksonclick(ActionEvent event) {

    }

}
