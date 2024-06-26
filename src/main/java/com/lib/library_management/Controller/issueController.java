package com.lib.library_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Utility.OpenWindow;
import com.lib.library_management.Utility.utilityClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    BooksEntityService booksEntityService;

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

    public void setRollNo(String RollNo) {
        studentId.setText(RollNo);
        studentId.setEditable(false);
    }

    @FXML
    void initialize() {
        Tableviewdemo.setPlaceholder(new Label("Requested Data is Not Available "));
        utilityClass.setIntegerLimiter(addingBookId, 7);
        takenBookId.setCellValueFactory(new PropertyValueFactory<BooksEntity, Integer>("BookId"));
        takenBookTitle.setCellValueFactory(new PropertyValueFactory<BooksEntity, String>("BookName"));
        takenBookEdition.setCellValueFactory(new PropertyValueFactory<BooksEntity, String>("Edition"));
        takenAuthor.setCellValueFactory(new PropertyValueFactory<BooksEntity, String>("Author"));
        Subject_Category.setCellValueFactory(new PropertyValueFactory<BooksEntity, String>("SubjectCategory"));
        takenBookCode.setCellValueFactory(new PropertyValueFactory<BooksEntity, Integer>("BookCode"));

    }

    @FXML
    void addBook(MouseEvent event) {
        try {

            Integer bookId = Integer.parseInt(addingBookId.getText());
            BooksEntity bookDetailsOptional = booksEntityService.getBookDataByBookId(bookId);

            // System.out.println(bookDetailsOptional);
            if (bookDetailsOptional != null) {
                ObservableList<BooksEntity> observableBookList = FXCollections.observableArrayList(bookDetailsOptional);
                observableBookList.forEach(book -> {
                    BookDetailsEntity details = book.getBookDetailsEntity();
                    book.setBookName(details.getBookName());
                    book.setEdition(details.getEdition());
                    book.setAuthor(details.getAuthor());
                    book.setSubjectCategory(details.getSubjectCategory());
                    book.setBookCode(details.getBookCode());
                    book.setBookId(bookId);

                });
                System.out.println(observableBookList);
                Tableviewdemo.setItems(observableBookList);
            } else {
                openWindow.openDialogue("Information", "Book ID does not exist.");
            }

        } catch (

        Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    void addBookToTable(MouseEvent event) {
        MainController mainController = new MainController();
        mainController.refreshTable();
    }

    @FXML
    void booksremove(MouseEvent event) {

    }

}
