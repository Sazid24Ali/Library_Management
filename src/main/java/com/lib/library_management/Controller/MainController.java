package com.lib.library_management.Controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Services.StudentService;
import com.lib.library_management.Utility.OpenWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.Year;

@Component
public class MainController {
    private static Map<Integer, String> courseMap;

    static {
        courseMap = new LinkedHashMap<>();
        courseMap.put(504, "(504) Computer Science ");
        courseMap.put(123, "(123) Applied Maths");
        courseMap.put(231, "(231) Pure Maths");
        courseMap.put(405, "(405) Maths With CS");
    }
    // To open New Windows
    @Autowired
    private OpenWindow openWindow;

    @Autowired
    StudentService studentService;

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
    private TableView<?> Stu_BooksDisplay_Table;

    @FXML
    private Button Student_Search_Btn;

    @FXML
    private Button Stu_Add_Btn;

    @FXML
    private Button Stu_EditDetails_Btn;

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
    private ComboBox<String> Student_Course_CBox;

    @FXML
    private TextField Student_RollNo_Field;

    @FXML
    private TextField Student_Year_Field;

    @FXML
    private Button issueBook_Btn;

    @FXML
    private Button returnBook_Btn;

    void VisibilitySetter(boolean value) {
        Stu_Add_Btn.setVisible(!value);
        Stu_Remove_Btn.setVisible(value);
        Stu_BooksDisplay_Table.setVisible(value);
        Stu_EditDetails_Btn.setVisible(value);
        issueBook_Btn.setVisible(value);
        returnBook_Btn.setVisible(value);

    }

    @FXML
    void initialize() {
        VisibilitySetter(false);
        Stu_Add_Btn.setVisible(false);
        // Stu_EditDetails_Btn.setVisible(false);
        ObservableList<String> observableCourses = FXCollections.observableArrayList(courseMap.values());
        Student_Course_CBox.setItems(observableCourses);
        setIntegerLimiter(Student_Year_Field, 2);
        setIntegerLimiter(Student_RollNo_Field, 4);
        setIntegerLimiter(Stu_PhNo_La_Field, 10);
        setIntegerLimiter(Stu_YOP_La_Field, 4);
    }

    private void setIntegerLimiter(TextField textField, int maxLength) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= maxLength) {
                return change;
            }
            return null;
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    @FXML
    void addNewBook(MouseEvent event) {

    }

    @FXML
    void addNewBookIDs(MouseEvent event) {

    }

    @FXML
    void addStudent(MouseEvent event) {
        boolean condition = Student_Course_CBox.getValue() != null && Student_RollNo_Field.getLength() >= 3
                && Student_Year_Field.getLength() >= 2 && Stu_Name_La_Field.getLength() >= 5
                && Stu_PhNo_La_Field.getLength() >= 10 && Stu_YOP_La_Field.getLength() >= 4;
        if (condition) {
            String course = Student_Course_CBox.getValue();
            String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                    + Student_RollNo_Field.getText();
            String Name = Stu_Name_La_Field.getText();
            String branch = course.substring(6);
            String PhNo = Stu_PhNo_La_Field.getText();
            Year YearOfPassing = Year.parse(Stu_YOP_La_Field.getText());
            StudentEntity newStudentData = new StudentEntity(RollNo, Name, PhNo, branch, YearOfPassing);

            System.out.println(newStudentData);

            // System.out.println("\n\n\n" + newStudentData.toString());

            if (studentService.addStudentData(newStudentData)) {
                openWindow.openDialogue("Success", " Add Student Data Successfully " + newStudentData);
                // Virtually Click the Search Button
                getStudentData(event);

            } else {
                openWindow.openDialogue("Try Again", "Data Was Not Added\n Try Again Later");
            }

        } else {
            openWindow.openDialogue("Information", "ENter the Data Correctly");

        }

    }

    @FXML
    void availableBooks(MouseEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AvailableBooks", "Available Books", primaryStage);

    }

    @FXML
    void getStudentData(MouseEvent event) {
        if (Student_Course_CBox.getValue() != null && Student_RollNo_Field.getLength() >= 3
                && Student_Year_Field.getLength() >= 2) {
            String course = Student_Course_CBox.getValue();
            String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                    + Student_RollNo_Field.getText();
            StudentEntity data = studentService.getStudentDataByRollNo(RollNo);
            if (data != null) {
                VisibilitySetter(true);
                newStudent(false);
                // System.out.println("\n\n\n\n" + data.getStudentName());
                Stu_Name_La_Field.setText(data.getStudentName());
                Stu_PhNo_La_Field.setText(String.valueOf(data.getPhoneNumber()));
                Stu_YOP_La_Field.setText(String.valueOf(data.getYearOfPassing()));
            } else {
                openWindow.openDialogue("Information", "Student Details Not Found \n Add Student");
                VisibilitySetter(false);
                newStudent(true);
            }
        } else {
            openWindow.openDialogue("Information",
                    "Enter the Correct Values of Student Roll No , Select Course and Student Year \n From The ID Card ");

        }
    }

    @FXML
    void editStudentData(MouseEvent event) {
        edit_Student_Data(true);

    }

    private void edit_Student_Data(boolean value) {
        Stu_Name_La_Field.setEditable(value);
        Stu_PhNo_La_Field.setEditable(value);
        // Year Of Passing Out
        // System.out.println("Year = " + String.valueOf(Year.now()).substring(0, 2) +
        // Student_Year_Field.getText());
        Stu_YOP_La_Field.setText(
                String.valueOf(Year.now()).substring(0, 2) + (Integer.valueOf(Student_Year_Field.getText()) + 2));
        Stu_YOP_La_Field.setEditable(value);
        Stu_Add_Btn.setVisible(value);
        Stu_EditDetails_Btn.setVisible(!value);

    }

    private void newStudent(boolean value) {
        Stu_Name_La_Field.clear();
        Stu_PhNo_La_Field.clear();
        Stu_YOP_La_Field.clear();
        Stu_Add_Btn.setVisible(value);
        edit_Student_Data(value);

    }

    @FXML
    void issueBook(MouseEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("Issue", "Issue Books", primaryStage);

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
