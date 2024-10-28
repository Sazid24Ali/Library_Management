package com.lib.library_management.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Services.BookDetailsService;
import com.lib.library_management.Services.BooksEntityService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

@Component
public class AvailableBooksController {

    @Autowired
    private BookDetailsService bookDetailsService;

    @Autowired
    private BooksEntityService booksService;

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
    @FXML
    private TableColumn<BookDetailsEntity, ArrayList<String>> borrowedByColumn;
    @FXML
    private TableColumn<BookDetailsEntity, Integer> pagesColumn;

    @FXML
    private TableColumn<BookDetailsEntity, String> placeAndpublisherColumn;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> priceColumn;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> publishYearColumn;

    @FXML
    private TableColumn<BookDetailsEntity, String> callNoColumn;

    @FXML
    private TableColumn<BookDetailsEntity, String> isbnColumn;


    private FilteredList<BookDetailsEntity> filteredList;

    public class CustomHeightTableCell<S, T> extends TableCell<S, T> {
        private static final double DEFAULT_HEIGHT = 5.0; // Adjust this value as needed
        private static final int CHAR_LIMIT = 32; // Number of characters after which to insert a new line

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
                setPrefHeight(USE_COMPUTED_SIZE);
            } else {
                String text = item.toString();
                text = insertNewLines(text, CHAR_LIMIT);
                Text wrappedText = new Text(text);
                wrappedText.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10));
                setGraphic(wrappedText);
                setPrefHeight(DEFAULT_HEIGHT + wrappedText.getLayoutBounds().getHeight());
            }
        }

        private String insertNewLines(String text, int limit) {
            StringBuilder sb = new StringBuilder(text);
            if (sb.charAt(0) == '[') {
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == ',') {
                        sb.insert(i + 1, '\n');
                    }

                }
                return sb.toString();
            }
            int i = limit;
            while (i < sb.length()) {
                sb.insert(i, "\n");
                i += limit + 1; // Move the index to the next limit position
            }
            return sb.toString();
        }
    }

    @FXML
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
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
        placeAndpublisherColumn.setCellValueFactory(new PropertyValueFactory<>("place_publisher"));
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishing_year"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        borrowedByColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedStudents"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN_no"));
        callNoColumn.setCellValueFactory(new PropertyValueFactory<>("Call_no"));

        // Apply custom TableCell to columns
        bookCodeColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        bookNameColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        authorColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        editionColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        subjectCategoryColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        totalBooksColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        availableBooksColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        borrowedBooksColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        bookIDsColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        pagesColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        placeAndpublisherColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        publishYearColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        priceColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        borrowedByColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        isbnColumn.setCellFactory(column -> new CustomHeightTableCell<>());
        callNoColumn.setCellFactory(column -> new CustomHeightTableCell<>());
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
            book.setBorrowedStudents(booksService.getBorrowedStudents(bookCode));
            book.setCall_no(booksService.getCallNO(bookCode));
            book.setISBN_no(booksService.getISBN_no(bookCode));
        });
        System.out.println("Book Details \n"+observableBookList);

        filteredList = new FilteredList<>(observableBookList, p -> true);
        // To add the functionality of Sorting the value in columns
        SortedList<BookDetailsEntity> sortableData = new SortedList<>(filteredList);
        DisplayAvailBooks_Table.setItems(sortableData);
        sortableData.comparatorProperty().bind(DisplayAvailBooks_Table.comparatorProperty());

        // DisplayAvailBooks_Table.setItems(filteredList);
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
                        || (book.getBookIds() != null && book.getBookIds().toString().contains(lowerCaseFilter))
                        || (book.getBorrowedStudents() != null
                                && book.getBorrowedStudents().toString().contains(lowerCaseFilter))
                        || (book.getISBN_no() != null
                                && book.getISBN_no().toString().contains(lowerCaseFilter))
                        || (book.getCall_no() != null
                                && book.getCall_no().toString().contains(lowerCaseFilter));
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
