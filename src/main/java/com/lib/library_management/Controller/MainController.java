package com.lib.library_management.Controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Component
public class MainController {

    @FXML
    private Button Std_Search_Btn;

    @FXML
    private ComboBox<?> Student_Course_CBox;

    @FXML
    private TextField Student_RollNo_Field;

    @FXML
    private TextField Student_Year_Field;

}
