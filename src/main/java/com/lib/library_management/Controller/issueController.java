package com.lib.library_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Services.StudentService;
import com.lib.library_management.Utility.OpenWindow;
import com.lib.library_management.Utility.utilityClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

@Component
public class issueController {
    @Autowired
    private OpenWindow openWindow;
    @Autowired
    private BooksEntityService booksEntityService;
    @Autowired
    private StudentService studentService;

    @FXML
    private Button addButton;

    @FXML
    private TextField addingBookId;

    @FXML
    private Button issueButton;

    @FXML
    private TextField studentId;

    @FXML
    private TableColumn<BooksEntity, Integer> takenBookId;
    @FXML
    private TableColumn<BooksEntity, String> takenBookTitle;
    @FXML
    private TableColumn<BooksEntity, String> Subject_Category;
    @FXML
    private TableColumn<BooksEntity, String> takenAuthor;
    @FXML
    private TableColumn<BooksEntity, String> takenBookEdition;
    @FXML
    private TableColumn<BooksEntity, Integer> takenBookCode;
    @FXML
    private TableView<BooksEntity> Tableviewdemo;
    @FXML
    private Button removebtn;

    private ObservableList<BooksEntity> observableBookList = FXCollections.observableArrayList();
    private List<Integer> addedBookIds = new ArrayList<>();
    private StudentEntity student;
    private MainController mainController;

    @FXML
    void booksremove(MouseEvent event) {
        try {
            removebtn.setDisable(true);
            BooksEntity selectedBook = Tableviewdemo.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                observableBookList.remove(selectedBook);
                addedBookIds.remove(selectedBook.getBookId());
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred while removing the book: " + e.getMessage());
        }
    }

    public void setRollNo(String RollNo) {
        studentId.setText(RollNo);
        studentId.setEditable(false);
        student = studentService.getStudentByRollNo(RollNo);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void initialize() {
        observableBookList.clear();
        addedBookIds.clear();
        removebtn.setDisable(true);
        Tableviewdemo.setPlaceholder(new Label("No Books Added"));
        utilityClass.setIntegerLimiter(addingBookId, 7);
        takenBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        takenBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        takenBookEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        takenAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        Subject_Category.setCellValueFactory(new PropertyValueFactory<>("subjectCategory"));
        takenBookCode.setCellValueFactory(new PropertyValueFactory<>("bookCode"));
        Tableviewdemo.setItems(observableBookList);
        Tableviewdemo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removebtn.setDisable(false);
            } else {
                removebtn.setDisable(true);
            }
        });
    }

    @FXML
    void addBook(ActionEvent event) {
        try {
            Integer bookId = Integer.parseInt(addingBookId.getText());
            boolean isBookAlreadyAdded = observableBookList.stream()
                    .anyMatch(book -> book.getBookId().equals(bookId));

            if (student != null && booksEntityService.isBookAlreadyBorrowed(bookId, student.getStudentRollNo())) {
                openWindow.openDialogue("Warning",
                        "Book ID " + bookId + " is already borrowed by the current student.");
                clearFields();
                return;
            }
            if (isBookAlreadyAdded) {
                openWindow.openDialogue("Warning", "Book ID " + bookId + " is already added.");
                clearFields();
                return;
            }
            if (booksEntityService.checkBookExistsById(bookId)) {
                BooksEntity bookDetailsOptional = booksEntityService.getBookDataByBookId(bookId);
                if (bookDetailsOptional != null) {
                    BookDetailsEntity details = bookDetailsOptional.getBookDetailsEntity();
                    if (details != null) {
                        BooksEntity book = new BooksEntity();
                        book.setBookId(bookId);
                        book.setBookName(details.getBookName());
                        book.setEdition(details.getEdition());
                        book.setAuthor(details.getAuthor());
                        book.setSubjectCategory(details.getSubjectCategory());
                        book.setBookCode(details.getBookCode());
                        observableBookList.add(book);
                        addedBookIds.add(bookId);
                        clearFields();
                    } else {
                        openWindow.openDialogue("Error", "Failed to retrieve book details for Book ID: " + bookId);
                    }
                } else {
                    openWindow.openDialogue("Error", "Failed to retrieve book details for Book ID: " + bookId);
                }
            } else {
                openWindow.openDialogue("Information", "Book ID does not exist.");
                clearFields();
            }
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Please enter a valid integer for Book ID.");
        } catch (Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void clearFields() {
        addingBookId.clear();
    }

    @FXML
    void addBookToTable(MouseEvent event) {
        try {
            LocalDate issueDate = LocalDate.now();
            boolean confirm = openWindow.openConfirmation("Confirmation", "Do you want to add these books?");
            if (confirm && student != null) {
                for (BooksEntity book : observableBookList) {
                    if (addedBookIds.contains(book.getBookId())) {
                        book.setStatus("Borrowed");
                        book.setDateOfAllotment(issueDate);
                        book.setStudent(student);

                        // Ensure that bookDetailsEntity is set
                        BooksEntity bookDetailsOptional = booksEntityService.getBookDataByBookId(book.getBookId());
                        if (bookDetailsOptional != null) {
                            BookDetailsEntity details = bookDetailsOptional.getBookDetailsEntity();
                            if (details != null) {
                                book.setBookDetailsEntity(details);
                            } else {
                                openWindow.openDialogue("Error",
                                        "Failed to retrieve book details for Book ID: " + book.getBookId());
                                return;
                            }
                        } else {
                            openWindow.openDialogue("Error",
                                    "Failed to retrieve book details for Book ID: " + book.getBookId());
                            return;
                        }
                    }
                }

                booksEntityService.saveOrUpdateBooks(observableBookList);

                observableBookList.clear();
                addedBookIds.clear();
                refreshTable();
            } else {
                System.out.println("Book addition canceled by user or no student selected.");
            }
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Please enter valid integers for Book IDs.");
        } catch (Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void refreshTable() {
        Tableviewdemo.setItems(FXCollections.observableArrayList(observableBookList));
        Tableviewdemo.refresh();
        mainController.getStudentData(null);

    }
}
