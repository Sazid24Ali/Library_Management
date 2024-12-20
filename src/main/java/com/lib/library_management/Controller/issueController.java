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
    private Button removebtn;

    @FXML
    private TextField studentId;

    @FXML
    private TableColumn<BooksEntity, String> takenBookId;
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

    private StudentEntity student;

    private ObservableList<BooksEntity> observableBookList = FXCollections.observableArrayList();
    private List<String> addedBookIds = new ArrayList<>();

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
        Tableviewdemo.getSelectionModel().clearSelection();
        removebtn.setDisable(true);
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
        // To have an fresh exeperience whenever we open the issue controller again
        observableBookList.clear();
        // addedBookIds.clear();

        Tableviewdemo.setPlaceholder(new Label("Requested Data is Not Available"));
        // utilityClass.setIntegerLimiter(addingBookId, 7);
        takenBookId.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        takenBookTitle.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        takenBookEdition.setCellValueFactory(new PropertyValueFactory<>("Edition"));
        takenAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        Subject_Category.setCellValueFactory(new PropertyValueFactory<>("SubjectCategory"));
        takenBookCode.setCellValueFactory(new PropertyValueFactory<>("BookCode"));
        Tableviewdemo.setItems(observableBookList);
        Tableviewdemo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removebtn.setDisable(false);
            } else {
                removebtn.setDisable(true);
            }
        });

        removebtn.setDisable(true);
    }

    @FXML
    void addBook(ActionEvent event) {
        try {
            // Integer bookId = Integer.parseInt(addingBookId.getText());
            String bookId  = addingBookId.getText().toUpperCase();
            boolean isBookAlreadyAdded = observableBookList.stream()
                    .anyMatch(book -> book.getBookId().equals(bookId));
            if (isBookAlreadyAdded) {
                openWindow.openDialogue("Warning", "Accession Number " + bookId + " is already Added To List.");

                clearFields();
                return;
            }

            if (booksEntityService.checkBookExistsById(bookId)) {
                if (booksEntityService.isBookAlreadyBorrowed(bookId)) {
                    String borrowerRollNo = booksEntityService.getBorrowerRollNo(bookId);
                    openWindow.openDialogue("Warning",
                            "Accession Number " + bookId + " is already borrowed by student with Roll No: " + borrowerRollNo);

                    clearFields();
                    return;
                }
                BooksEntity bookDetailsOptional = booksEntityService.getBookDataByBookId(bookId);
                if (bookDetailsOptional != null) {
                    BookDetailsEntity details = bookDetailsOptional.getBookDetailsEntity();
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
                    openWindow.openDialogue("Error", "Failed to retrieve book details for Accession Number: " + bookId);
                }
            } else {
                openWindow.openDialogue("Information", "Accession Number does not exist.");
                clearFields();
            }
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Please enter a  Accession Number.");
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
        if (observableBookList.isEmpty()) {
            openWindow.openDialogue("No Books", "No Books Added \nAdd the Books ");
            return;
        }
        try {
            LocalDate issueDate = LocalDate.now();
            boolean confirm = openWindow.openConfirmation("Confirmation", "Do you want to add the books?");
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
                                        "Failed to retrieve book details for Accession Number: " + book.getBookId());
                                return;
                            }
                        } else {
                            openWindow.openDialogue("Error",
                                    "Failed to retrieve book details for Accession Number: " + book.getBookId());
                            return;
                        }

                    }
                }

                booksEntityService.saveOrUpdateBooks(observableBookList);
                // observableBookList.clear();
                // addedBookIds.clear();
                refreshTable();
                initialize();
            } else {
                // System.out.println("Book addition canceled by user.");
                openWindow.openDialogue("Cancel", "Book Addition Cancelled By User");
            }
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Please enter valid  Accession Numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void refreshTable() {
        List<BooksEntity> filteredBooks = new ArrayList<>();
        for (BooksEntity book : observableBookList) {
            if (addedBookIds.contains(book.getBookId())) {
                filteredBooks.add(book);
            }
        }
        ObservableList<BooksEntity> filteredObservableList = FXCollections.observableArrayList(filteredBooks);
        Tableviewdemo.setItems(filteredObservableList);
        mainController.getStudentData(null);

    }

}
