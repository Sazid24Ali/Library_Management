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

    @Autowired
    BooksEntityRepo booksEntityRepo;

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
        String inputIds=inputOfBookIds.getText();
        String[] ArrayOfIds;
        ArrayList<Integer> Ids=new ArrayList<>();
        int j=0;
        if(inputIds.contains(",") && inputIds.contains("-")){
            ArrayOfIds = inputIds.split(","); 
            for(String myStr: ArrayOfIds){
                if(myStr.contains("-")){
                    String s[]=myStr.split("-");
                    int lowerRangeOfId=Integer.parseInt(s[0]),upperRangeOfId=Integer.parseInt(s[1]);
                    for(int i=lowerRangeOfId;i<upperRangeOfId+1;i++)
                        Ids.add(i);
                }
                else{
                    Ids.add(Integer.parseInt(myStr));
                }
            }
        }
        else if(inputIds.contains("-")){
            String s[]=inputIds.split("-");
            int lowerRangeOfId=Integer.parseInt(s[0]),upperRangeOfId=Integer.parseInt(s[1]);
            for(int i=lowerRangeOfId;i<upperRangeOfId+1;i++)
                Ids.add(i);
        }
        else if(inputIds.contains(",")){
            ArrayOfIds = inputIds.split(","); 
            for(String s:ArrayOfIds)
                Ids.add(Integer.parseInt(s));
        }
        else{
            Ids.add(Integer.parseInt(inputIds));
        }
        ArrayList<BooksEntity> booksToAdd=new ArrayList<BooksEntity>();
        for(Integer id:Ids){
            if(booksEntityRepo.getBookIds().contains(id)){
                openWindow.openDialogue("Warning", "You have entered an Id: "+id+" which is already in records." );
            }
            else{
                BooksEntity booksEntity=new BooksEntity();
                booksEntity.setBookId(id);
                booksEntity.setStatus("Available");
                booksEntity.setDateOfAllotment(null);
                booksEntity.setBookDetailsEntity(bookDetailsEntity);
                booksToAdd.add(booksEntity);
            }
        }
        bookEntityService.addBooks(booksToAdd);
        openWindow.openDialogue("Info", "The id's: "+Ids+" have been inserted into database within "+bookCode.getText()+" Book code");
        bookCode.clear();
        inputOfBookIds.clear();
        tableToShowBook.getItems().clear();
    }
}
