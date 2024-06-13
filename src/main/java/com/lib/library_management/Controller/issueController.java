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
import javafx.scene.control.TableCell;
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
    private TextField studentId;

    @FXML
    private TableColumn<BooksEntity, String> takenBookEdition;

    @FXML
    private TableColumn<BooksEntity, Integer> takenBookId;

    @FXML
    private TableColumn<BooksEntity, String> takenBookTitle;

    @FXML
    private TableView<BooksEntity> Tableviewdemo;

    public class CustomHeightTableCell<S, T> extends TableCell<S, T> {
        private static final double DEFAULT_HEIGHT = 5.0; // Adjust this value as needed
        private static final int CHAR_LIMIT = 30;
    }

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

        takenBookId.setCellFactory(column -> new CustomHeightTableCell<>());
        takenBookTitle.setCellFactory(column -> new CustomHeightTableCell<>());
        takenBookEdition.setCellFactory(column -> new CustomHeightTableCell<>());

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

    }

}
