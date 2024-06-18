package com.lib.library_management.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
        courseMap.put(393, "Faculty");
    }
    ObservableList<String> observableCourses = FXCollections.observableArrayList(courseMap.values());
    // To open New Windows
    @Autowired
    private OpenWindow openWindow;

    private BooksEntity booksEntity;

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
    private Button Fact_Add_Btn;

    @FXML
    private TextField Fact_Dept_La_Field;

    @FXML
    private Label Fact_Dept_Label;

    @FXML
    private Button Fact_EditDetails_Btn;

    @FXML
    private TextField Fact_Name_La_Field;

    @FXML
    private Label Fact_Name_Label;

    @FXML
    private TextField Fact_PhNo_La_Field;

    @FXML
    private Label Fact_PhNo_Label;

    @FXML
    private Label Fact_Position_Label;

    @FXML
    private Button Fact_Remove_Btn;

    @FXML
    private TextField Fact_Position_La_Field;

    @FXML
    private GridPane FacultyPane;

    @FXML
    private GridPane StudentPane;

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

    @FXML
    private TableColumn<BooksEntity, Integer> pagesColumn;

    @FXML
    private TableColumn<BooksEntity, String> placeAndPublisherColumn;

    @FXML
    private TableColumn<BooksEntity, Integer> priceColumn;

    @FXML
    private TableColumn<BooksEntity, Integer> publishingYearColumn;

    private Stage primaryStage;
    private boolean isEditing = false;

    // Setter method to set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Override the close request
        this.primaryStage.setOnCloseRequest(event -> {
            if (isEditing) {
                // Prevent the window from closing
                event.consume();

                // Show a warning message
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Close Prevented");
                alert.setHeaderText(null);
                alert.setContentText("You cannot close the window while editing.");
                alert.showAndWait();
            }
        });
    }

    void initialState() {
        // Headers Setting
        Student_RollNo_Field.clear();
        Student_Year_Field.clear();

        // Student_Course_CBox.getSelectionModel().clearSelection();
        Student_RollNo_Field.setPromptText("Roll No");
        Student_Year_Field.setPromptText("Year");

        // Students Fields
        StudentPane.setVisible(true);
        Stu_Add_Btn.setVisible(false);
        Stu_Add_Btn.setText("Add Student");

        Stu_Name_La_Field.clear();
        Stu_PhNo_La_Field.clear();
        Stu_YOP_La_Field.clear();
        Stu_Name_La_Field.setEditable(false);
        Stu_PhNo_La_Field.setEditable(false);
        Stu_YOP_La_Field.setEditable(false);

        Stu_Remove_Btn.setVisible(false);
        Stu_EditDetails_Btn.setVisible(false);

        // FacultyFields
        FacultyPane.setVisible(false);
        Fact_Add_Btn.setVisible(false);
        Fact_Add_Btn.setText("Add Faculty");

        Fact_Name_La_Field.clear();
        Fact_PhNo_La_Field.clear();
        Fact_Position_La_Field.clear();
        Fact_Name_La_Field.setEditable(false);
        Fact_PhNo_La_Field.setEditable(false);
        Fact_Position_La_Field.setEditable(false);

        Fact_Remove_Btn.setVisible(false);
        Fact_EditDetails_Btn.setVisible(false);

    }

    void VisibilitySetter(boolean value) {

        // isEditing = !value;
        Fact_Add_Btn.setVisible(value);
        Fact_EditDetails_Btn.setVisible(value);
        Fact_Remove_Btn.setVisible(value);

        Stu_Add_Btn.setVisible(!value);
        Stu_Remove_Btn.setVisible(value);
        Stu_EditDetails_Btn.setVisible(value);

        Stu_BooksDisplay_Table.setVisible(value);

        issueBook_Btn.setVisible(value);
        returnBook_Btn.setVisible(value);

        issueBook_Btn.setDisable(value);
        returnBook_Btn.setDisable(value);
        Stu_Remove_Btn.setDisable(value);

        Stu_BooksDisplay_Table.setDisable(value);

        Admin_AddNewBookIds_Btn.setDisable(value);
        Admin_AddNewBook_Btn.setDisable(value);
        Admin_AvailableBooks_Btn.setDisable(value);
        Admin_RemoveBooks_Btn.setDisable(value);

    }

    void defaultSettings() {
        VisibilitySetter(false);
        Student_Course_CBox.getSelectionModel().clearSelection();
        initialState();
    }

    @FXML
    void initialize() {
        // Mapping the Table Columns to the BooksEntity
        Stu_BooksDisplay_Table.setPlaceholder(new Label("No Books Taken "));
        // Stu_BooksDisplay_Table.setEditable(true);

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

        pagesColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleIntegerProperty(booksEntity.getBookDetailsEntity().getPages()).asObject();
        });

        placeAndPublisherColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleStringProperty(booksEntity.getBookDetailsEntity().getPlace_publisher());
        });

        priceColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleIntegerProperty(booksEntity.getBookDetailsEntity().getPrice()).asObject();
        });

        publishingYearColumn.setCellValueFactory(cellData -> {
            BooksEntity booksEntity = cellData.getValue();
            return new SimpleIntegerProperty(booksEntity.getBookDetailsEntity().getPublishing_year()).asObject();
        });

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
        // VisibilitySetter(false);
        // Setting the Combo box
        initializeComboBox();
        // setting the fields length
        setFelidLengths();

    }

    void initializeComboBox() {
        try {
            Stu_BooksDisplay_Table.getSelectionModel().clearSelection();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Student_Course_CBox.setItems(observableCourses);
    }

    void setFelidLengths() {
        utilityClass.setIntegerLimiter(Student_Year_Field, 2);
        utilityClass.setIntegerLimiter(Student_RollNo_Field, 4);
        utilityClass.setIntegerLimiter(Stu_PhNo_La_Field, 10);
        utilityClass.setIntegerLimiter(Stu_YOP_La_Field, 4);

        utilityClass.setIntegerLimiter(Fact_PhNo_La_Field, 10);

    }

    @FXML
    void setPane(ActionEvent event) {
        // System.out.println("Got in here ");
        String scope = Student_Course_CBox.getValue();
        String RollNo = Student_RollNo_Field.getText();
        String Year = Student_Year_Field.getText();
        initialState();
        VisibilitySetter(false);
        if (scope == "Faculty") {
            Fact_Add_Btn.setVisible(false);
            Student_Course_CBox.setValue(scope);
            Student_Year_Field.setDisable(true);
            Student_RollNo_Field.setPromptText("CE ID");
            StudentPane.setVisible(false);
            FacultyPane.setVisible(true);
        } else {
            Stu_Add_Btn.setVisible(false);
            if (RollNo.length() == 0 && Year.length() == 0) {
                Student_Course_CBox.setValue(scope);
                Student_Year_Field.setDisable(false);
            } else {
                Student_Course_CBox.setValue(scope);
                Student_Year_Field.setDisable(false);
                Student_Year_Field.setText(Year);
                Student_RollNo_Field.setText(RollNo);
            }
        }
    }

    @FXML
    void addNewBook(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AddNewBooks", "Add New Books ", primaryStage);

    }

    @FXML
    void addNewBookIDs(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AppendBooks", "Append New Books ", primaryStage);

    }

    void addNewFact_stud(StudentEntity newData, String fact_stud) {
        // System.out.println("Add New Student Fact value is " + isEditing);
        isEditing = false;

        System.out.println(newData);
        // System.out.println("\n\n\n" + newStudentData.toString());
        if (studentService.addStudentData(newData)) {
            openWindow.openDialogue("Success", " Added " + fact_stud + " Data Successfully " + newData);
            // Virtually Click the Search Button
            ActionEvent event = new ActionEvent();
            getStudentData(event);
        } else {
            openWindow.openDialogue("Try Again", "Data Was Not Added\n Try Again Later");
        }

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
            StudentEntity newStudentData = new StudentEntity(RollNo, Name, PhNo, branch, YearOfPassing, null);

            addNewFact_stud(newStudentData, "Student");
        } else {
            openWindow.openDialogue("Information", "Enter the Data Correctly");
        }
    }

    @FXML
    void addFaculty(ActionEvent event) {
        boolean condition = Student_RollNo_Field.getLength() >= 4 && Fact_Name_La_Field.getLength() >= 5
                && Fact_PhNo_La_Field.getLength() >= 10 && Fact_Position_La_Field.getLength() >= 5;
        if (condition) {
            String branch = Student_Course_CBox.getValue();
            String RollNo = Student_RollNo_Field.getText();
            String facultyName = Fact_Name_La_Field.getText();
            String PhNo = Fact_PhNo_La_Field.getText();
            // Year YearOfPassing = null;
            String faculty_Position = Fact_Position_La_Field.getText();

            StudentEntity newFaculty = new StudentEntity(RollNo, facultyName, PhNo, branch, null,
                    faculty_Position);
            addNewFact_stud(newFaculty, "Faculty");
        } else {
            openWindow.openDialogue("Information", "Enter the Data Correctly");
        }

    }

    @FXML
    void availableBooks(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("AvailableBooks", "Available Books", primaryStage);

    }

    void setStudentData(String course) {
        // Checking the Year is >=2 indirectly
        if (Student_Year_Field.getLength() < 2) {
            openWindow.openDialogue("Warning", "Enter the Year");
            return;
        }
        String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                + Student_RollNo_Field.getText();
        StudentPane.setVisible(true);

        StudentEntity data = studentService.getStudentDataByRollNo(RollNo);
        if (data != null) {
            VisibilitySetter(true);
            newStudent(false);
            returnBook_Btn.setDisable(true);
            System.out.println("\n\n\n\n" + data.getYearOfPassing());
            Stu_Name_La_Field.setText(data.getStudentName());
            Stu_PhNo_La_Field.setText(String.valueOf(data.getPhoneNumber()));
            Stu_YOP_La_Field.setText(String.valueOf(data.getYearOfPassing()));

            setIssuedbooks(RollNo);
        } else {
            openWindow.openDialogue("Information", "Student Details Not Found \n Add Student");
            VisibilitySetter(false);
            newStudent(true);
        }
    }

    void setFacultyData() {
        String RollNo = Student_RollNo_Field.getText();
        FacultyPane.setVisible(true);

        StudentEntity data = studentService.getStudentDataByRollNo(RollNo);
        if (data != null) {
            System.out.println("Set Faculty Data Got data");
            VisibilitySetter(true);
            newFaculty(false);
            returnBook_Btn.setDisable(true);
            System.out.println("\n\n\n\n" + data.getYearOfPassing());
            Fact_Name_La_Field.setText(data.getStudentName());
            Fact_PhNo_La_Field.setText(String.valueOf(data.getPhoneNumber()));
            Fact_Position_La_Field.setText(data.getFacultyPosition());

            setIssuedbooks(RollNo);
        } else {
            openWindow.openDialogue("Information", "Faculty Details Not Found \n Add Faculty");
            VisibilitySetter(false);
            newFaculty(true);
        }

    }

    @FXML
    void getStudentData(ActionEvent event) {
        String course = Student_Course_CBox.getValue();
        if (course != null && Student_RollNo_Field.getLength() >= 3) {
            if (course == "Faculty") {
                setFacultyData();
            } else {
                setStudentData(course);
            }
        } else {
            if (course == "Faculty") {
                openWindow.openDialogue("Information",
                        "Enter the Correct Value of CE ID From The ID Card ");
            } else {
                openWindow.openDialogue("Information",
                        "Enter the Correct Values of Student Roll No , Select Course and Student Year \n From The ID Card ");
            }
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

    @FXML
    void editFacultyData(ActionEvent event) {
        edit_Faculty_Data(true);
    }

    private void edit_Faculty_Data(boolean value) {
        Fact_Name_La_Field.setEditable(value);
        Fact_PhNo_La_Field.setEditable(value);
        Fact_Position_La_Field.setEditable(value);

        Fact_Add_Btn.setVisible(value);
        Fact_Remove_Btn.setDisable(value);
        Fact_EditDetails_Btn.setVisible(!value);
        Fact_Add_Btn.setText("Save Data");
        edit_Fact_Stud(value);

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
        Stu_Remove_Btn.setDisable(value);
        Stu_EditDetails_Btn.setVisible(!value);
        Stu_Add_Btn.setText("Save Data");
        edit_Fact_Stud(value);
    }

    private void edit_Fact_Stud(boolean value) {
        // System.out.println("edit_fact_student value is " + isEditing + " boolean is " + value);
        isEditing = value;

        issueBook_Btn.setDisable(value);
        returnBook_Btn.setDisable(value);
        Stu_BooksDisplay_Table.getSelectionModel().clearSelection();
        Stu_BooksDisplay_Table.setDisable(value);

        Admin_AddNewBookIds_Btn.setDisable(value);
        Admin_AddNewBook_Btn.setDisable(value);
        Admin_AvailableBooks_Btn.setDisable(value);
        Admin_RemoveBooks_Btn.setDisable(value);
    }

    private void newFaculty(boolean value) {
        Fact_Name_La_Field.clear();
        Fact_PhNo_La_Field.clear();
        Fact_Position_La_Field.clear();
        Fact_Add_Btn.setVisible(value);
        Fact_Add_Btn.setText("Save Data ");
        edit_Faculty_Data(value);
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

        setIssuedbooks(RollNo);
        // Added this to clear the selection from the table and reset the return button
        // to disable
        Stu_BooksDisplay_Table.getSelectionModel().clearSelection();
        returnBook_Btn.setDisable(true);

    }

    @FXML
    void removeBooks(ActionEvent event) {
        defaultSettings();
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        openWindow.openScene("RemoveRecords", "Remove Books ", primaryStage);

    }

    @FXML
    void removeStudent(ActionEvent event) {
        String course = Student_Course_CBox.getValue();
        String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                + Student_RollNo_Field.getText();

        removeFact_Stud(RollNo, "Student");
    }

    void removeFact_Stud(String ID, String fact_stud) {

        if (openWindow.openConfirmation("Remove",
                "Are you sure want to Remove the " + fact_stud + " With ID \"" + ID + "\"")) {
            StudentEntity removeStudent = studentService.deleteById(ID);

            if (removeStudent != null) {
                openWindow.openDialogue("Deleted Successfully",
                        "" + fact_stud + " Removed Successfully \n" + removeStudent);
                defaultSettings();

            } else {
                openWindow.openDialogue("Deletion Unsuccessful",
                        "" + fact_stud + " With " + ID + " ID Cannot Be removed \nThere are books to be Returned ");
            }
        }
    }

    @FXML
    void removeFaculty(ActionEvent event) {
        String RollNo = Student_RollNo_Field.getText();
        removeFact_Stud(RollNo, "Faculty");

    }

    @FXML
    void selectedBook(MouseEvent event) {
        booksEntity = new BooksEntity();
        booksEntity = Stu_BooksDisplay_Table.getSelectionModel().getSelectedItem();
        Stu_BooksDisplay_Table.setStyle("-fx-selection-bar: blue; -fx-selection-bar-non-focused: salmon;");
        if (booksEntity == null) {
            returnBook_Btn.setDisable(true);
        } else {
            returnBook_Btn.setDisable(false);
        }

    }

    @FXML
    public void returnBook(MouseEvent event) {
        if (booksEntity != null) {
            BookDetailsEntity bookDetailsEntity = new BookDetailsEntity();
            bookDetailsEntity = booksEntity.getBookDetailsEntity();
            String bookTitle = bookDetailsEntity.getBookName();
            String bookEdition = bookDetailsEntity.getEdition();
            String bookAuthor = bookDetailsEntity.getAuthor();
            // String subjectCategory=bookDetailsEntity.getSubjectCategory();
            Boolean boolean1 = openWindow.openConfirmation("Warning",
                    "Do you want to return the Selected Book?" + "\n" + "Book Name: " + bookTitle + "\n" +
                            "Book Edition: " + bookEdition + "\n" + "Book Author: " + bookAuthor);
            if (boolean1) {
                Stu_BooksDisplay_Table.getItems().remove(booksEntity);
                booksEntity.setStatus("Available");
                booksEntity.setStudent(null);
                booksService.saveReturningBook(booksEntity);
            }
        } else {
            openWindow.openDialogue("Info", "Please select any of the record(s) to perform return option.");
        }
        // Added this to clear the selection from the table and reset the return button
        // to disable
        Stu_BooksDisplay_Table.getSelectionModel().clearSelection();
        returnBook_Btn.setDisable(true);
    }

    public void refreshTable() {
        String course = Student_Course_CBox.getValue();
        String RollNo = 1007 + Student_Year_Field.getText() + course.substring(1, 4)
                + Student_RollNo_Field.getText();
        setIssuedbooks(RollNo);
        Stu_BooksDisplay_Table.refresh();
        // Stu_BooksDisplay_Table.getSelectionModel().clearSelection();
    }
}
