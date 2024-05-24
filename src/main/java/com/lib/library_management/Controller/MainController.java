package com.lib.library_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Utility.OpenWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

@Component
public class MainController {

    // To open New Windows
    @Autowired
    private OpenWindow openWindow;

    @FXML
    private Button Admin_AddNewBookIds_Btn;

    @FXML
    private Button Admin_AddNewBook_Btn;

    @FXML
    private Button Admin_AvailableBooks_Btn;

    @FXML
    private Label Admin_Label;

    @FXML
    private Button Admin_RemoveBooks_Btn;

    @FXML
    private TableView<?> Std_BooksDisplay_Table;

    @FXML
    private Button Std_Search_Btn;

    @FXML
    private Button Stu_Add_Btn;

    @FXML
    private TextField Stu_Name_La_Field;

    @FXML
    private Label Stu_Name_Label;

    @FXML
    private TextField Stu_PhNo_La_Field;

    @FXML
    private Label Stu_PhNo_Label;

    @FXML
    private Button Stu_Remove_Btn;

    @FXML
    private TextField Stu_YOP_La_Field;

    @FXML
    private Label Stu_YOP_Label;

    @FXML
    private ComboBox<?> Student_Course_CBox;

    @FXML
    private TextField Student_RollNo_Field;

    @FXML
    private TextField Student_Year_Field;

    @FXML
    private Button issueBook_Btn;

    @FXML
    private Button returnBook_Btn;

    @FXML
    void addNewBook(MouseEvent event) {

    }

    @FXML
    void addNewBookIDs(MouseEvent event) {

    }

    @FXML
    void addStudent(MouseEvent event) {

    }

    @FXML
    void availableBooks(MouseEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AvailableBooks", "Available Books", primaryStage);

    }

    @FXML
    void edit_stu_details(MouseEvent event) {

    }

    @FXML
    void getStudentData(MouseEvent event) {

    }

    @FXML
    void issueBook(MouseEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("Issue", "Isuue Books", primaryStage);

    }

    @FXML
    void removeBooks(MouseEvent event) {

    }

    @FXML
    void removeStudent(MouseEvent event) {

    }

    @FXML
    void returnBook(MouseEvent event) {

    }

}
