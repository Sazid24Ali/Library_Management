package com.lib.library_management.Controller;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Component
public class AddNewBooksController {

    @FXML
    private Button save;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfbookcode;

    @FXML
    private TextField tfbookname;

    @FXML
    private TextField tfedition;

    @FXML
    private TextField tfpages;

    @FXML
    private TextField tfplaceandpublisher;

    @FXML
    private TextField tfprice;

    @FXML
    private TextField tfsubjectcategory;

    @FXML
    private TextField tfyear;

    @FXML
    void onclicksave(ActionEvent event) {

    }

}
