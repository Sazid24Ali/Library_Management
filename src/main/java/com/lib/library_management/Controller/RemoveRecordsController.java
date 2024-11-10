package com.lib.library_management.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Utility.OpenWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class RemoveRecordsController {

    @Autowired
    private BooksEntityService booksService;

    @Autowired
    private OpenWindow openWindow;

    @FXML
    private TableColumn<BooksEntity, Integer> colBookcode;

    @FXML
    private TableColumn<BooksEntity, String> colbookid;

    @FXML
    private TableColumn<BooksEntity, String> colbookname;

    @FXML
    private TableColumn<BooksEntity, String> colauthor;

    @FXML
    private TableColumn<BooksEntity, String> coledition;

    @FXML
    private TableColumn<BooksEntity, String> colsubject;

    @FXML
    private RadioButton rdBookcode;

    @FXML
    private RadioButton rdBookid;

    @FXML
    private Button remove;

    @FXML
    private Button searchbtn;

    @FXML
    private TextField tfid_bookcode;

    @FXML
    private TableView<BooksEntity> removebooktable;

    private ObservableList<BooksEntity> observableBookList = FXCollections.observableArrayList();
    private Map<String, String> booksNotRemoved = new HashMap<>();

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {
        removebooktable.setEditable(false);
        observableBookList.clear();

        colbookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colbookname.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colauthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        coledition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        colsubject.setCellValueFactory(new PropertyValueFactory<>("subjectCategory"));
        colBookcode.setCellValueFactory(new PropertyValueFactory<>("bookCode"));

        rdBookcode.setToggleGroup(toggleGroup);
        rdBookid.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == rdBookid) {
                tfid_bookcode.setPromptText("Enter Accession Number");
            } else if (newToggle == rdBookcode) {
                tfid_bookcode.setPromptText("Enter Book Code");
            }
            clearTableAndData();
        });
    }

    @FXML
    void onclickcontentsvisible(ActionEvent event) {
        String input = tfid_bookcode.getText().trim();

        if (!rdBookid.isSelected() && !rdBookcode.isSelected()) {
            openWindow.openDialogue("Warning", "Please select either Accession Number or Book Code.");
            return;
        }

        if (input.isEmpty()) {
            openWindow.openDialogue("Warning", "Please enter a Accession Number or Book code.");
            return;
        }

        try {
            if (rdBookid.isSelected()) {
                // Integer bookId = Integer.parseInt(input);
                String bookId = input.toUpperCase();
                if (observableBookList.stream().anyMatch(book -> book.getBookId().equals(bookId))) {
                    openWindow.openDialogue("Warning", "Accession Number " + bookId + " already exists in the table.");
                    tfid_bookcode.clear();
                    return;
                }
                BooksEntity bookDetailsOptional = booksService.getBookDataByBookId(bookId);
                if (bookDetailsOptional != null) {
                    BookDetailsEntity details = bookDetailsOptional.getBookDetailsEntity();
                    BooksEntity book = new BooksEntity();
                    book.setBookId(bookId);
                    book.setBookName(details.getBookName());
                    book.setEdition(details.getEdition());
                    book.setAuthor(details.getAuthor());
                    book.setSubjectCategory(details.getSubjectCategory());
                    book.setBookCode(details.getBookCode());
                    book.setStudent(bookDetailsOptional.getStudent());
                    observableBookList.add(book);
                } else {
                    openWindow.openDialogue("Warning", "Accession Number " + bookId + " does not exist.");
                }
            } else if (rdBookcode.isSelected()) {
                Long bookCode = Long.parseLong(input);
                if (observableBookList.stream().anyMatch(book -> book.getBookCode() == bookCode)) {
                    openWindow.openDialogue("Warning", "Book Code " + bookCode + " already exists in the table.");
                    clearTableAndData();
                    return;
                }
                String response = booksService.getISBN_no(bookCode);
                // System.out.println("The response from ISBN : "+response);
                List<BooksEntity> books = booksService.getBooksByCode(bookCode);
                if (books.isEmpty()) {
                    if (response != null) {
                        boolean confirm = openWindow.openConfirmation("Warning",
                                "No Books Are Present\nDo you want to Remove the Book Details ");
                        if (confirm) {
                            booksService.deleteBookDetailsByCode(bookCode);
                            openWindow.openDialogue("Message", "Removed the Book Details Of Book Code : " + bookCode);
                        } else {
                            openWindow.openDialogue("Message", "User Cancelled The Operation");
                        }
                    } else {
                        openWindow.openDialogue("Warning", "Book Code '" + bookCode + "' Not Found ");
                    }
                } else {
                    for (BooksEntity bookDetailsOptional : books) {
                        BookDetailsEntity details = bookDetailsOptional.getBookDetailsEntity();
                        BooksEntity book = new BooksEntity();
                        book.setBookId(bookDetailsOptional.getBookId());
                        book.setBookName(details.getBookName());
                        book.setEdition(details.getEdition());
                        book.setAuthor(details.getAuthor());
                        book.setSubjectCategory(details.getSubjectCategory());
                        book.setBookCode(details.getBookCode());
                        observableBookList.add(book);
                    }
                }
            }
            removebooktable.setItems(observableBookList);
            tfid_bookcode.clear();
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            openWindow.openDialogue("Error", "An error occurred while searching books: " + e.getMessage());
        }
    }

    @FXML
    void removebooksonclick(ActionEvent event) {
        booksNotRemoved.clear();
        if (observableBookList.isEmpty()) {

            openWindow.openDialogue("Warning", "No books selected for removal.");
            return;
        }
        boolean confirm = openWindow.openConfirmation("Confirmation",
                "Do you want to permanently remove the selected books?");
        if (confirm) {
            try {
                if (rdBookid.isSelected()) {
                    List<String> removedBookIds = new ArrayList<>();
                    for (BooksEntity book : observableBookList) {
                        if (book.getStudent() == null) {
                            booksService.deleteBook(book.getBookId());
                            removedBookIds.add(book.getBookId());
                        } else {
                            booksNotRemoved.put(book.getBookId(), book.getStudent().getStudentRollNo());
                        }
                    }
                    if (!booksNotRemoved.isEmpty()) {
                        openWindow.openDialogue("Message",
                                "Can't Delete As they where issued\n" +
                                        booksNotRemoved.entrySet().stream()
                                                .map(entry -> "Accession Number: " + entry.getKey()
                                                        + ", Student Roll No: "
                                                        + entry.getValue())
                                                .collect(Collectors.joining("\n")));
                    }
                    if (!removedBookIds.isEmpty()) {
                        openWindow.openDialogue("Deleted Books",
                                " Accession Numbers:\n " +
                                        removedBookIds.stream()
                                                .map(Object::toString)
                                                .collect(Collectors.joining(", ")));
                    }
                } else if (rdBookcode.isSelected()) {
                    List<String> removedBookIds = new ArrayList<>();
                    List<Long> validBookCodes = observableBookList.stream()
                            .filter(book -> book.getBookCode() != null)
                            .map(BooksEntity::getBookCode)
                            .distinct()
                            .collect(Collectors.toList());

                    for (Long bookCode : validBookCodes) {
                        List<BooksEntity> booksToDelete = booksService.getBooksByCode(bookCode);
                        for (BooksEntity book : booksToDelete) {
                            if (book.getStudent() == null) {
                                booksService.deleteBook(book.getBookId());
                                removedBookIds.add(book.getBookId());
                            } else {
                                booksNotRemoved.put(book.getBookId(), book.getStudent().getStudentRollNo());
                            }
                        }
                        boolean allBooksNotBorrowed = booksToDelete.stream()
                                .allMatch(book -> book.getStudent() == null);
                        if (allBooksNotBorrowed) {
                            booksService.deleteBookDetailsByCode(bookCode);
                        }
                    }
                    if (!booksNotRemoved.isEmpty()) {
                        openWindow.openDialogue("Message",
                                "Can't Delete As they where issued \n" +
                                        booksNotRemoved.entrySet().stream()
                                                .map(entry -> "Accession Number: " + entry.getKey()
                                                        + ", Student Roll No: "
                                                        + entry.getValue())
                                                .collect(Collectors.joining("\n")));
                    }
                    if (!removedBookIds.isEmpty()) {
                        openWindow.openDialogue("Deleted Books",
                                " Accession Numbers: \n" +
                                        removedBookIds.stream()
                                                .map(Object::toString)
                                                .collect(Collectors.joining(", ")));
                    }
                }
                clearTableAndData();
            } catch (Exception e) {
                e.printStackTrace();
                openWindow.openDialogue("Error", "An error occurred while removing books: " + e.getMessage());
            }
        }
    }

    private void clearTableAndData() {
        observableBookList.clear();
        removebooktable.getItems().clear();
        tfid_bookcode.clear();
    }
}
