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
    private TableColumn<BooksEntity, Integer> colbookid;

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
    private Map<Integer,String> BooksNotremoved = new HashMap();

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
                tfid_bookcode.setPromptText("Enter Book ID");
            } else if (newToggle == rdBookcode) {
                tfid_bookcode.setPromptText("Enter Book Code");
            }
        });
    }

    @FXML
    void onclickcontentsvisible(ActionEvent event) {
        String input = tfid_bookcode.getText().trim();

        if (!rdBookid.isSelected() && !rdBookcode.isSelected()) {
            openWindow.openDialogue("Warning", "Please select either Book ID or Book Code.");
            return;
        }

        if (input.isEmpty()) {
            openWindow.openDialogue("Warning", "Please enter a book ID or code.");
            return;
        }

        try {
            if (rdBookid.isSelected()) {
                Integer bookId = Integer.parseInt(input);
                if (observableBookList.stream().anyMatch(book -> book.getBookId() == bookId)) {
                    openWindow.openDialogue("Warning", "Book ID " + bookId + " already exists in the table.");
                    return;
                }
                // TODO : The Logic Need to be Setted here` 
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
                    if (observableBookList.contains(book)) {
                        openWindow.openDialogue("Warning", "Book ID " + bookId + " is already added.");
                        return;
                    }

                    observableBookList.add(book);
                } else {
                    openWindow.openDialogue("Warning", "Book ID " + bookId + " does not exist.");
                }
            } else if (rdBookcode.isSelected()) {
                Integer bookCode = Integer.parseInt(input);

                if (observableBookList.stream().anyMatch(book -> book.getBookCode() == bookCode)) {
                    openWindow.openDialogue("Warning", "Book Code " + bookCode + " already exists in the table.");
                    return;
                }

                List<BooksEntity> books = booksService.getBooksByCode(bookCode);
                System.out.println(books);
                if (books.isEmpty()) {
                    openWindow.openDialogue("Warning", "No books found with Book Code " + bookCode);
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
                        if (observableBookList.contains(book)) {
                            openWindow.openDialogue("Warning", "Book Code " + bookCode + " is already added.");
                            return;
                        }

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
        BooksNotremoved.clear();
        if (observableBookList.isEmpty()) {
            openWindow.openDialogue("Warning", "No books selected for removal.");
            return;
        }
        boolean confirm = openWindow.openConfirmation("Confirmation",
                "Do you want to permanently remove the selected books?");
        if (confirm) {
            try {
                if (rdBookid.isSelected()) {
                    // if (observableBookList.isEmpty()) {
                    // openWindow.openDialogue("Warning", "No books selected for removal.");
                    // return;
                    // }
                    for (BooksEntity book : observableBookList) {
                        System.out.println("To Be Removed : \n"+book);
                        if (book.getStudent() == null){
                            booksService.deleteBook(book.getBookId());
                        }else{
                            BooksNotremoved.put(book.getBookId(),book.getStudent().getStudentRollNo());
                        }
                    }
                    if (!BooksNotremoved.isEmpty()) {
                        openWindow.openDialogue("Message", "The Following Ids Were Not Removed\nThis Books Are Issued to Students/Faculty\n"+BooksNotremoved);
                        return;
                    }
                }
                if (rdBookcode.isSelected()) {
                    List<Integer> validBookCodes = observableBookList.stream()
                            .filter(book -> book.getBookCode() != null)
                            .map(BooksEntity::getBookCode)
                            .collect(Collectors.toList());

                    if (validBookCodes.isEmpty()) {
                        openWindow.openDialogue("Warning", "No valid book codes selected for removal.");
                        return;
                    }

                    for (Integer bookCode : validBookCodes) {
                        booksService.deleteBookDetailsByCode(bookCode);
                    }
                }
                observableBookList.clear();
                removebooktable.getItems().clear();
                openWindow.openDialogue("Info", "Books have been removed successfully.");
                tfid_bookcode.clear();
            } catch (Exception e) {
                e.printStackTrace();
                openWindow.openDialogue("Error", "An error occurred while removing books: " + e.getMessage());
            }
        }
    }
}
