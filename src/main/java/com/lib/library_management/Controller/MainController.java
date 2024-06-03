package com.lib.library_management.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Services.StudentService;
import com.lib.library_management.Utility.OpenWindow;
import com.lib.library_management.Utility.utilityClass;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.Year;

@Component
public class MainController {
    private static Map<Integer, String> courseMap;

    static {
        courseMap = new LinkedHashMap<>();
        courseMap.put(504, "(504) Computer Science ");
        courseMap.put(231, "(505) Pure Maths");
        courseMap.put(123, "(506) Applied Maths");
        courseMap.put(405, "(586) Maths With CS");
    }
    // To open New Windows
    @Autowired
    private OpenWindow openWindow;

    private BooksEntity booksEntity=new BooksEntity();

    @Autowired
    StudentService studentService;

    @Autowired
    BooksEntityService booksService;

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

    @FXML
    private TableView<BooksEntity> Stu_BooksDisplay_Table;

    @FXML
    private TableColumn<BooksEntity, String> BookNameColumn;

    @FXML
    private TableColumn<BooksEntity, String> DateOfAllotmentColumn;

    @FXML
    private TableColumn<BooksEntity, String> authorColumn;

    @FXML
    private TableColumn<BooksEntity, Integer> bookCodeColumn;

    @FXML
    private TableColumn<BooksEntity, String> bookIdColumn;

    @FXML
    private TableColumn<BooksEntity, String> editionColumn;

    void VisibilitySetter(boolean value) {
        Stu_Add_Btn.setVisible(!value);
        Stu_Remove_Btn.setVisible(value);
        Stu_BooksDisplay_Table.setVisible(value);
        Stu_EditDetails_Btn.setVisible(value);
        issueBook_Btn.setVisible(value);
        returnBook_Btn.setVisible(value);
        issueBook_Btn.setDisable(value);
        returnBook_Btn.setDisable(value);
        Stu_Remove_Btn.setDisable(value);

    }

    void defaultSettings() {
        VisibilitySetter(false);
        Stu_Add_Btn.setVisible(false);
        Student_RollNo_Field.clear();
        Student_Year_Field.clear();
        Student_Course_CBox.getSelectionModel().clearSelection();
        Student_RollNo_Field.setPromptText("Roll No");
        Student_Year_Field.setPromptText("Year");
        Stu_Name_La_Field.clear();
        Stu_PhNo_La_Field.clear();
        Stu_YOP_La_Field.clear();
        Stu_Name_La_Field.setEditable(false);
        Stu_PhNo_La_Field.setEditable(false);
        Stu_YOP_La_Field.setEditable(false);

    }

    @FXML
    void initialize() {
        // Mapping the Table Columns to the BooksEntity
        Stu_BooksDisplay_Table.setPlaceholder(new Label("No Books Taken "));
        Stu_BooksDisplay_Table.setEditable(true);

        bookCodeColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleIntegerProperty(booksEntity.getBookDetailsEntity().getBookCode()).asObject();
        });
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        authorColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleStringProperty(booksEntity.getBookDetailsEntity().getAuthor());
        });
        BookNameColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleStringProperty(booksEntity.getBookDetailsEntity().getBookName());
        });
        editionColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleStringProperty(booksEntity.getBookDetailsEntity().getEdition());
        });

        DateOfAllotmentColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfAllotment"));

        // To get the default select "Select the Course" when any admin btn is clicked
        Student_Course_CBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(Student_Course_CBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
        VisibilitySetter(false);
        Stu_Add_Btn.setVisible(false);
        // Stu_EditDetails_Btn.setVisible(false);
        ObservableList<String> observableCourses = FXCollections.observableArrayList(courseMap.values());
        Student_Course_CBox.setItems(observableCourses);
        utilityClass.setIntegerLimiter(Student_Year_Field, 2);
        utilityClass.setIntegerLimiter(Student_RollNo_Field, 4);
        utilityClass.setIntegerLimiter(Stu_PhNo_La_Field, 10);
        utilityClass.setIntegerLimiter(Stu_YOP_La_Field, 4);

    }

    @FXML
    void addNewBook(ActionEvent event) {

    }

    @FXML
    void addNewBookIDs(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AppendBooks", "Append New Books ", primaryStage);
    }

    @FXML
    void addStudent(ActionEvent event) {
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
    void availableBooks(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AvailableBooks", "Available Books", primaryStage);

    }

    @FXML
    void getStudentData(ActionEvent event) {
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
                setIssuedbooks(RollNo);
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

    private void setIssuedbooks(String RollNo) {
        List<BooksEntity> booksData = booksService.getBooksFromStudentRollNo(RollNo);
        // System.out.println(booksData);
        ObservableList<BooksEntity> observableBooksList = FXCollections.observableArrayList(booksData);

        Stu_BooksDisplay_Table.setItems(observableBooksList);

    }

    @FXML
    void editStudentData(ActionEvent event) {
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
        issueBook_Btn.setDisable(value);
        returnBook_Btn.setDisable(value);
        Stu_Remove_Btn.setDisable(value);

    }

    private void newStudent(boolean value) {
        Stu_Name_La_Field.clear();
        Stu_PhNo_La_Field.clear();
        Stu_YOP_La_Field.clear();
        Stu_Add_Btn.setVisible(value);
        edit_Student_Data(value);

    }

    @FXML
    void issueBook(ActionEvent event) {
        // if the edit is clicked and then the issue is clicked then the felids are
        // forever in edit mode
        // edit_Student_Data(false);
        String course = Student_Course_CBox.getValue();
        String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                + Student_RollNo_Field.getText();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("Issue", "Issue Books", primaryStage, RollNo);

    }

    @FXML
    void removeBooks(ActionEvent event) {

    }

    @FXML
    void removeStudent(ActionEvent event) {
        String course = Student_Course_CBox.getValue();
        String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                + Student_RollNo_Field.getText();

        if (openWindow.openConfirmation("Remove",
                "Are you sure want to Remove the Student With Roll No \"" + RollNo + "\"")) {
            StudentEntity removeStudent = studentService.deleteById(RollNo);

            if (removeStudent != null) {
                openWindow.openDialogue("Deleted Successfully",
                        "Student Record Removed Successfully \n" + removeStudent);
                defaultSettings();

            } else {
                openWindow.openDialogue("Deletion Unsuccessful",
                        "Student With " + RollNo + " Roll Number Cannot Be removed \nThere are books to be Returned ");
            }
        }

    }

    @FXML
    void selectedBook(MouseEvent event) {
        booksEntity = Stu_BooksDisplay_Table.getSelectionModel().getSelectedItem();
        openWindow.openDialogue("Info", "You have selected a Book from List of issued Books.");
    }

    @FXML
    void returnBook(MouseEvent event) {
        Boolean b=openWindow.openConfirmation("Warning", "Do you want to declare Returning of the Selected Book?");
        if(b){
            Stu_BooksDisplay_Table.getItems().remove(booksEntity);
            booksEntity.setStatus("Available");
        }
    }

}
