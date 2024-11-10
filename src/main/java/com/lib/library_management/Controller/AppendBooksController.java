package com.lib.library_management.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Repository.BooksEntityRepo;
import com.lib.library_management.Services.BooksDetailsService;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Utility.OpenWindow;
import com.lib.library_management.Utility.utilityClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

@Component
public class AppendBooksController {
    @Autowired
    BooksDetailsService bookDetailsService;

    @Autowired
    BooksEntityService bookEntityService;

    @Autowired
    OpenWindow openWindow;

    @Autowired
    BooksEntityRepo booksEntityRepo;

    BookDetailsEntity bookDetailsEntity = new BookDetailsEntity();
    BooksEntity booksEntity = new BooksEntity();

    @FXML
    private TextField bookCode;

    @FXML
    private TextArea disclaimer;

    @FXML
    private TextArea inputOfBookIds;

    @FXML
    private Button saveButtonForScene;

    @FXML
    private Button searchWithBookCode;

    @FXML
    private TableView<BookDetailsEntity> tableToShowBook;

    @FXML
    private TableColumn<BookDetailsEntity, String> edition;

    @FXML
    private TableColumn<BookDetailsEntity, String> bookTitle;

    @FXML
    private TableColumn<BookDetailsEntity, String> author;

    @FXML
    private TableColumn<BookDetailsEntity, String> sub_category;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> BookCode;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> pagesColumn;

    @FXML
    private TableColumn<BookDetailsEntity, String> placeAndPublisherColumn;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> priceColumn;

    @FXML
    private TableColumn<BookDetailsEntity, Integer> publishingYearColumn;

    @FXML
    void initialize() {
        tableToShowBook.setEditable(false);
        inputOfBookIds.setDisable(true);
        saveButtonForScene.setDisable(true);
        bookTitle.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("BookName"));
        author.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("Author"));
        edition.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("Edition"));
        BookCode.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, Integer>("BookCode"));
        sub_category.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("SubjectCategory"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, Integer>("pages"));
        placeAndPublisherColumn
                .setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("place_publisher"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, Integer>("price"));
        publishingYearColumn
                .setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, Integer>("publishing_year"));
        // For Inter Only input
        utilityClass.setIntegerLimiter(bookCode, 5);

    }

    @FXML
    void searchForBook(ActionEvent event) {
        Boolean condition = bookCode.getLength() != 0;
        if (condition) {
            Long Bcode = Long.parseLong(bookCode.getText());
            bookDetailsEntity = bookDetailsService.getBookDetailsByBookCode(Bcode);
            if (bookDetailsEntity != null) {
                display(bookDetailsEntity);
            } else {
                tableToShowBook.getItems().clear();
                inputOfBookIds.clear();
                inputOfBookIds.setDisable(true);
                saveButtonForScene.setDisable(true);
                openWindow.openDialogue("Warning",
                        "Enter the Correct Book Code\n\nBooks Code is Not Available Add It in \" Add New Book \" Panel ");
                bookCode.requestFocus();
            }
        } else {
            openWindow.openDialogue("Warning",
                    "Enter the Book Code\n");
            bookCode.requestFocus();
        }
    }

    @FXML
    void display(BookDetailsEntity bDE) {
        ObservableList<BookDetailsEntity> bDetails = FXCollections.observableArrayList(bDE);
        tableToShowBook.setItems(bDetails);
        inputOfBookIds.setDisable(false);
        saveButtonForScene.setDisable(false);
        inputOfBookIds.requestFocus();
    }

    @FXML
    public void getBookId(MouseEvent event) {
        if (bookDetailsEntity != null && bookCode.getLength() != 0) {
            Boolean boolForInvalidInput = false;
            // inputOfBookIds.setText(bookCode.getText());
            String userGivenIds = inputOfBookIds.getText().trim();
            if (userGivenIds.isEmpty()) {
                openWindow.openDialogue("Warning", "Enter the Accession Numbers");
                inputOfBookIds.requestFocus();
                return;

            }
            // Boolean boolForInvalidRange = false;
            if (!userGivenIds.contains(",")) {
                userGivenIds = userGivenIds + ",";
            }
            String[] idsSplitByComma = userGivenIds.split(",");
            ArrayList<String> idsWithoutLastCharAdded = new ArrayList<>();
            for (String idSplitByComma : idsSplitByComma)
                idsWithoutLastCharAdded.add(idSplitByComma.toUpperCase());
            // idsWithoutLastCharAdded.remove(idsWithoutLastCharAdded.size()-1);
            ArrayList<String> Ids = idsWithoutLastCharAdded;

            // System.out.println("DEBUG  ::: " + idsWithoutLastCharAdded + "\n" + Ids);
            Set<String> IdSet = new LinkedHashSet<>();
            IdSet.addAll(Ids);
            Ids.clear();
            Ids.addAll(IdSet);
            // System.out.println("DEBUG :: " + Ids + "\nID SET :: " + IdSet);
            ArrayList<BooksEntity> booksToAdd = new ArrayList<BooksEntity>();
            ArrayList<String> idsNotAdded = new ArrayList<>();
            ArrayList<String> idsAdded = new ArrayList<>();
            Boolean boolForNonUniqueIds = false;
            if (!boolForInvalidInput) {
                for (int i = 0; i < Ids.size(); i++) {
                    if (booksEntityRepo.getBookIds().contains(Ids.get(i))) {
                        boolForNonUniqueIds = true;
                        idsNotAdded.add(Ids.get(i));
                    } else {
                        BooksEntity booksEntity = new BooksEntity();
                        booksEntity.setBookId(Ids.get(i));
                        booksEntity.setStatus("Available");
                        booksEntity.setDateOfAllotment(null);
                        booksEntity.setBookDetailsEntity(bookDetailsEntity);
                        booksToAdd.add(booksEntity);
                        idsAdded.add(Ids.get(i));
                    }
                }
                if (!boolForNonUniqueIds) {
                    Collections.sort(idsAdded);
                    Boolean boolForEntryRequest = openWindow.openConfirmation("Info",
                            "The id's: \n" + idsAdded + " \n\n Will be assigned to " + bookCode.getText()
                                    + " Book code.\n\n Do you want to proceed?\n");
                    if (boolForEntryRequest) {
                        try {
                            bookEntityService.addBooks(booksToAdd);
                            inputOfBookIds.clear();
                            openWindow.openDialogue("Info",
                                    "The Id's : \n " + idsAdded + " \n\n were successfully Added ");// into
                            // the
                            // database.");
                            bookDetailsEntity = null;
                            // bookCode.setText(null);
                        } catch (Exception e) {
                            openWindow.openDialogue("Issue", "There is some issue with the server.");
                        }
                    } else {
                        inputOfBookIds.requestFocus();
                    }
                } else {
                    Collections.sort(idsNotAdded);
                    openWindow.openDialogue("Warning", "You have entered Ids: " + idsNotAdded
                            + " which are already in records.\n\nNote: You can view the added books with Accession Numbers through Available books option by searching with Accession Numbers or Book Code.");
                    inputOfBookIds.requestFocus();
                }
            }
        } else {
            openWindow.openDialogue("Warning", "Please enter the Book code and click on search Button.");
        }
    }
}

// With the Range Function THing is in this
// @FXML
// public void getBookId(MouseEvent event) {
// if (bookDetailsEntity != null && bookCode.getLength() != 0) {
// Boolean boolForInvalidInput = false;
// // inputOfBookIds.setText(bookCode.getText());
// String userGivenIds = inputOfBookIds.getText().trim();
// if (userGivenIds.isEmpty()) {
// openWindow.openDialogue("Warning", "Enter the Book Ids");
// inputOfBookIds.requestFocus();
// return;

// }
// Boolean boolForInvalidRange = false;
// if (!userGivenIds.contains(",")) {
// userGivenIds = userGivenIds + ",";
// }
// String[] idsSplitByComma = userGivenIds.split(",");
// ArrayList<String> idsWithoutLastCharAdded = new ArrayList<>();
// for (String idSplitByComma : idsSplitByComma)
// idsWithoutLastCharAdded.add(idSplitByComma);
// // idsWithoutLastCharAdded.remove(idsWithoutLastCharAdded.size()-1);
// ArrayList<Integer> Ids = new ArrayList<>();
// for (String idOrRangeOfIds : idsWithoutLastCharAdded) {
// try {
// if (idOrRangeOfIds.contains("-")) {
// String rangeOfIds[] = idOrRangeOfIds.split("-");
// int lowerRangeOfId = Integer.parseInt(rangeOfIds[0]),
// upperRangeOfId = Integer.parseInt(rangeOfIds[1]);
// if (lowerRangeOfId >= upperRangeOfId) {
// openWindow.openDialogue("Warning", "The given range of Ids is Invalid. Please
// edit. ");
// boolForInvalidRange = true;
// inputOfBookIds.requestFocus();
// break;
// } else {
// while (upperRangeOfId >= lowerRangeOfId) {
// Ids.add(upperRangeOfId);
// upperRangeOfId--;
// }
// }
// } else {
// Ids.add(Integer.parseInt(idOrRangeOfIds));
// }
// } catch (Exception e) {
// openWindow.openDialogue("Warning",
// "The given inputs or the given input format is invalid.\n\n Please check if
// the input are only numbers in the \n\nmentioned format below the textBox.");
// boolForInvalidInput = true;
// inputOfBookIds.requestFocus();
// break;
// }
// }
// Set<Integer> IdSet = new LinkedHashSet<>();
// IdSet.addAll(Ids);
// Ids.clear();
// Ids.addAll(IdSet);
// ArrayList<BooksEntity> booksToAdd = new ArrayList<BooksEntity>();
// ArrayList<Integer> idsNotAdded = new ArrayList<>();
// ArrayList<Integer> idsAdded = new ArrayList<>();
// Boolean boolForNonUniqueIds = false;
// if (!boolForInvalidRange && !boolForInvalidInput) {
// for (int i = 0; i < Ids.size(); i++) {
// if (booksEntityRepo.getBookIds().contains(Ids.get(i))) {
// boolForNonUniqueIds = true;
// idsNotAdded.add(Ids.get(i));
// } else {
// BooksEntity booksEntity = new BooksEntity();
// booksEntity.setBookId(Ids.get(i));
// booksEntity.setStatus("Available");
// booksEntity.setDateOfAllotment(null);
// booksEntity.setBookDetailsEntity(bookDetailsEntity);
// booksToAdd.add(booksEntity);
// idsAdded.add(Ids.get(i));
// }
// }
// if (!boolForNonUniqueIds) {
// Collections.sort(idsAdded);
// Boolean boolForEntryRequest = openWindow.openConfirmation("Info",
// "The id's: \n" + idsAdded + " \n\n Will be assigned to " + bookCode.getText()
// + " Book code.\n\n Do you want to proceed?\n");
// if (boolForEntryRequest) {
// try {
// bookEntityService.addBooks(booksToAdd);
// inputOfBookIds.clear();
// openWindow.openDialogue("Info",
// "The Id's : \n " + idsAdded + " \n\n were successfully Added ");// into
// // the
// // database.");
// bookDetailsEntity = null;
// // bookCode.setText(null);
// } catch (Exception e) {
// openWindow.openDialogue("Issue", "There is some issue with the server.");
// }
// } else {
// inputOfBookIds.requestFocus();
// }
// } else {
// Collections.sort(idsNotAdded);
// openWindow.openDialogue("Warning", "You have entered Ids: " + idsNotAdded
// + " which are already in records.\n\nNote: You can view the added books with
// Book Ids through Available books option by searching with Book Ids or Book
// Code.");
// inputOfBookIds.requestFocus();
// }
// }
// } else {
// openWindow.openDialogue("Warning", "Please enter the Book code and click on
// search Button.");
// }
// }
// }
