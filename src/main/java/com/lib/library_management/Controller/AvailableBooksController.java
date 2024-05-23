package com.lib.library_management.Controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class AvailableBooksController {

    @FXML
    private Label AvailableBooks_Label;

    @FXML
    private TableView<?> DisplayAvailBooks_Table;

    @FXML
    private Label Search_AvailBooks_Label;

    @FXML
    private TextField Search_AvailBooks_TextField;

}
