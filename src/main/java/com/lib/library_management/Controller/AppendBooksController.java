package com.lib.library_management.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Services.BooksDetailsService;
import com.lib.library_management.Services.BooksEntityService;

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
        String Ids=inputOfBookIds.getText();
        String[] ArrayOfIds = Ids.split(",");  
        ArrayList<BooksEntity> booksToAdd=new ArrayList<BooksEntity>();
        for(String myStr: ArrayOfIds) {
          if(myStr.contains("-")){
            int l,h;
            String[] s=myStr.split("-");
            l=Integer.parseInt(s[0]);
            h=Integer.parseInt(s[1]);
            for(int i=l;i<h+1;i++){
                BooksEntity booksEntity=new BooksEntity();
                booksEntity.setBookId(i);
                booksEntity.setStatus("Available");
                booksEntity.setBookDetailsEntity(bookDetailsEntity);
                booksToAdd.add(booksEntity);

            }
          }
          else{
            BooksEntity booksEntity=new BooksEntity();
            booksEntity.setBookId(Integer.parseInt(myStr));
            booksEntity.setStatus("Available");
            booksEntity.setBookDetailsEntity(bookDetailsEntity);
            booksToAdd.add(booksEntity);
            }
       }
       bookEntityService.addBooks(booksToAdd);
    }
}

/*Unsaved transient entity: ([com.lib.library_management.Entity.BookDetailsEntity#<null>])
Dependent entities: ([[com.lib.library_management.Entity.BooksEntity#19]])
Non-nullable association(s): ([com.lib.library_management.Entity.BooksEntity.bookDetailsEntity])*/
