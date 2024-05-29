package com.lib.library_management.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Services.BookDetailsService;
import com.lib.library_management.Services.BooksService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class AvailableBooksController {

    @Autowired
    private BookDetailsService bookDetailsService;

    @Autowired
    private BooksService booksService;

    @FXML
    private Label AvailableBooks_Label;

    @FXML
    private Label Search_AvailBooks_Label;

    @FXML
    private TextField Search_AvailBooks_TextField;

    @FXML
    private TableView<BookDetailsEntity> DisplayAvailBooks_Table;
    @FXML
    private TableColumn<BookDetailsEntity, Integer> bookCodeColumn;
    @FXML
    private TableColumn<BookDetailsEntity, String> bookNameColumn;
    @FXML
    private TableColumn<BookDetailsEntity, String> authorColumn;
    @FXML
    private TableColumn<BookDetailsEntity, String> editionColumn;
    @FXML
    private TableColumn<BookDetailsEntity, String> subjectCategoryColumn;
    @FXML
    private TableColumn<BookDetailsEntity, Long> totalBooksColumn;
    @FXML
    private TableColumn<BookDetailsEntity, Long> availableBooksColumn;
    @FXML
    private TableColumn<BookDetailsEntity, Long> borrowedBooksColumn;
    @FXML
    private TableColumn<BookDetailsEntity, ArrayList<String>> bookIDsColumn;

    private FilteredList<BookDetailsEntity> filteredList;

    public void initialize() {
        // message to be displayed when the table is empty
        DisplayAvailBooks_Table.setPlaceholder(new Label("Requested Data is Not Available "));

        // Associating with the class
        bookCodeColumn.setCellValueFactory(new PropertyValueFactory<>("BookCode"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        editionColumn.setCellValueFactory(new PropertyValueFactory<>("Edition"));
        subjectCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("SubjectCategory"));
        totalBooksColumn.setCellValueFactory(new PropertyValueFactory<>("totalBooks"));
        availableBooksColumn.setCellValueFactory(new PropertyValueFactory<>("availableBooks"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));
        bookIDsColumn.setCellValueFactory(new PropertyValueFactory<>("bookIds"));

        loadBookData();
        setupFiltering();
    }

    private void loadBookData() {
        List<BookDetailsEntity> bookList = bookDetailsService.getAllBooks();
        ObservableList<BookDetailsEntity> observableBookList = FXCollections.observableArrayList(bookList);

        observableBookList.forEach(book -> {
            Integer bookCode = book.getBookCode();
            book.setTotalBooks(booksService.countTotalBooks(bookCode));
            book.setAvailableBooks(booksService.countAvailableBooks(bookCode));
            book.setBorrowedBooks(booksService.countBorrowedBooks(bookCode));
            book.setBookIds(booksService.getBookIds(bookCode));
        });

        filteredList = new FilteredList<>(observableBookList, p -> true);
        DisplayAvailBooks_Table.setItems(filteredList);

        // DisplayAvailBooks_Table.setItems(observableBookList);
    }

    private void setupFiltering() {
        Search_AvailBooks_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                return (book.getBookCode() != null && book.getBookCode().toString().contains(lowerCaseFilter))
                        || (book.getBookName() != null && book.getBookName().toLowerCase().contains(lowerCaseFilter))
                        || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerCaseFilter))
                        || (book.getEdition() != null && book.getEdition().toLowerCase().contains(lowerCaseFilter))
                        || (book.getSubjectCategory() != null
                                && book.getSubjectCategory().toLowerCase().contains(lowerCaseFilter))
                        || (book.getBookIds() != null && book.getBookIds().toString().contains(lowerCaseFilter));
                // the below line are commented Because we don't want to filter the books based
                // on the Number of books available , borrowed and available.
                // || (book.getTotalBooks() != null &&
                // book.getTotalBooks().toString().contains(lowerCaseFilter))
                // || (book.getAvailableBooks() != null
                // && book.getAvailableBooks().toString().contains(lowerCaseFilter))
                // || (book.getBorrowedBooks() != null
                // && book.getBorrowedBooks().toString().contains(lowerCaseFilter))
            });
        });
    }
}
