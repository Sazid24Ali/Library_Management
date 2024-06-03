package com.lib.library_management.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Services.BooksDetailsService;
import com.lib.library_management.Services.BooksEntityService;
import com.lib.library_management.Utility.OpenWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    BookDetailsEntity bookDetailsEntity=new BookDetailsEntity();
    BooksEntity booksEntity=new BooksEntity();

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
    void searchForBook(MouseEvent event) {
        boolean condition=bookCode.getLength()!=0;
        if(condition){
            Integer Bcode=Integer.parseInt(bookCode.getText());
            bookDetailsEntity= bookDetailsService.getBookDetailsByBookCode(Bcode);
            display(bookDetailsEntity);
        }
    }
    
    @FXML
    void display(BookDetailsEntity bDE) {
        tableToShowBook.setEditable(false);
        final ObservableList<BookDetailsEntity> bDetails = FXCollections.observableArrayList(bDE);
        bookTitle.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("BookName"));
        author.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("Author"));
        edition.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("Edition"));
        BookCode.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, Integer>("BookCode"));
        sub_category.setCellValueFactory(new PropertyValueFactory<BookDetailsEntity, String>("SubjectCategory"));
        tableToShowBook.setItems(bDetails);
    }

    @FXML
    public void getBookId(MouseEvent event) {
        String message="Do you really want to add these Id's of books for Book code "+bookCode.getText();
        Boolean bool=openWindow.openConfirmation("Warning", message);
        if(bool){
            int l=0,h=0;
            String Ids=inputOfBookIds.getText();
            String[] ArrayOfIds = Ids.split(",");  
            ArrayList<BooksEntity> booksToAdd=new ArrayList<BooksEntity>();
            int f=0;
            for(String myStr: ArrayOfIds) {
                if(myStr.contains("-")){
                    String[] s=myStr.split("-");
                    l=Integer.parseInt(s[0]);
                    h=Integer.parseInt(s[1]);
                    if(l>=h && l!=0 && h!=0){
                        for(int i=l;i<h+1;i++){
                            BooksEntity booksEntity=new BooksEntity();
                            booksEntity.setBookId(i);
                            booksEntity.setStatus("Available");
                            booksEntity.setBookDetailsEntity(bookDetailsEntity);
                            booksToAdd.add(booksEntity);
                        }
                    }
                    else{
                        f++;
                    }
                }
                else{
                    BooksEntity booksEntity=new BooksEntity();
                    booksEntity.setBookId(Integer.parseInt(myStr));
                    booksEntity.setStatus("Available");
                    booksEntity.setBookDetailsEntity(bookDetailsEntity);
                    booksToAdd.add(booksEntity);
                }
                if(f!=0){
                    openWindow.openDialogue("Info", "Please enter valid range of Id's. Values between "+String.valueOf(l)+" and"+String.valueOf(h)+" doesn't exist.");
                }
            }
            bookEntityService.addBooks(booksToAdd);
            openWindow.openDialogue("Info", "The id's "+Ids+" have been inserted into database within "+bookCode.getText()+" Book code");
            bookCode.clear();
            inputOfBookIds.clear();
            tableToShowBook.getItems().clear();
        }
    }
}
