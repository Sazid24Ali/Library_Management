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
        if(bookDetailsEntity!=null){
            String userGivenIds=inputOfBookIds.getText();
            Boolean boolForInvalidRange=false;
            if(userGivenIds.charAt(userGivenIds.length()-1)!=','){
                userGivenIds=userGivenIds+", ";
            }
            String[] idsSplitByComma=userGivenIds.split(",");
            ArrayList<String> idsWithoutLastCharAdded=new ArrayList<>();
            for(String idSplitByComma:idsSplitByComma)
                idsWithoutLastCharAdded.add(idSplitByComma);
            idsWithoutLastCharAdded.remove(idsWithoutLastCharAdded.size()-1);
            ArrayList<Integer> Ids=new ArrayList<>();
            for(String idOrRangeOfIds:idsWithoutLastCharAdded){
                if(idOrRangeOfIds.contains("-")){
                    String rangeOfIds[]=idOrRangeOfIds.split("-");
                    int lowerRangeOfId=Integer.parseInt(rangeOfIds[0]),upperRangeOfId=Integer.parseInt(rangeOfIds[1]);
                    if(lowerRangeOfId>=upperRangeOfId){
                        openWindow.openDialogue("Warning", "The given range of Ids is Invalid. Please edit. ");
                        boolForInvalidRange=true;
                        inputOfBookIds.requestFocus();
                        break;
                    }
                    else{
                        while(upperRangeOfId>=lowerRangeOfId){
                            Ids.add(upperRangeOfId);
                            upperRangeOfId--;
                        }
                    }
                }
                else{
                    Ids.add(Integer.parseInt(idOrRangeOfIds));
                }
            }
            ArrayList<BooksEntity> booksToAdd=new ArrayList<BooksEntity>();
            ArrayList<Integer> idsNotAdded=new ArrayList<>();
            Boolean boolForNonUniqueIds=false;
            if(!boolForInvalidRange){
                for(int i=0;i<Ids.size()-1;i++){
                    if(booksEntityRepo.getBookIds().contains(Ids.get(i))){
                        boolForNonUniqueIds=true;
                        idsNotAdded.add(Ids.get(i));
                        Ids.remove(Ids.get(i));
                    }
                    else{
                        BooksEntity booksEntity=new BooksEntity();
                        booksEntity.setBookId(Ids.get(i));
                        booksEntity.setStatus("Available");
                        booksEntity.setDateOfAllotment(null);
                        booksEntity.setBookDetailsEntity(bookDetailsEntity);
                        booksToAdd.add(booksEntity);
                    }
                }
                if(boolForNonUniqueIds){
                    openWindow.openDialogue("Warning", "You have entered Ids: "+idsNotAdded+" which are already in records.\n\nNote: You can view the added books with Book Ids through Available books option by searching with Book Ids or Book Code." );
                }
                Boolean boolForEntryRequest=openWindow.openConfirmation("Info", "The id's: "+Ids+" are successfully being able to add into database within "+bookCode.getText()+" Book code.\nThe Ids "+idsNotAdded+" are not being able to be added into database as there exists books with Ids given. Do you want to proceed?");
                if(boolForEntryRequest){
                    try{
                        bookEntityService.addBooks(booksToAdd);
                        inputOfBookIds.clear();
                        openWindow.openDialogue("Info", "The records are successfully entered into the database.");
                        bookDetailsEntity=null;
                    }
                    catch(Exception e){
                        openWindow.openDialogue("Issue", "There is some issue with the server.");
                    }
                }
                else{
                    inputOfBookIds.requestFocus();
                }
            }
        }
        else{
            openWindow.openDialogue("Warning", "Please enter the Book code and click on search Button.");
        }
    }
}
// String inputIds=inputOfBookIds.getText();
        // String[] ArrayOfIds;
        // ArrayList<Integer> Ids=new ArrayList<>();
        // Boolean boolForValidRange=false;
        // if(inputIds.contains(",") && inputIds.contains("-")){
        //     ArrayOfIds = inputIds.split(","); 
        //     for(String myStr: ArrayOfIds){
        //         if(myStr.contains("-")){
        //             String s[]=myStr.split("-");
        //             int lowerRangeOfId=Integer.parseInt(s[0]),upperRangeOfId=Integer.parseInt(s[1]);
        //             if(lowerRangeOfId>=upperRangeOfId){
        //                 boolForValidRange=true;
        //             }
        //             else{
        //                 for(int i=lowerRangeOfId;i<upperRangeOfId+1;i++)
        //                     Ids.add(i);
        //             }
        //         }
        //         else{
        //             Ids.add(Integer.parseInt(myStr));
        //         }
        //     }
        // }
        // else if(inputIds.contains("-")){
        //     String s[]=inputIds.split("-");
        //     int lowerRangeOfId=Integer.parseInt(s[0]),upperRangeOfId=Integer.parseInt(s[1]);
        //     if(lowerRangeOfId>=upperRangeOfId){
        //         boolForValidRange=true;
        //     }
        //     else{
        //         for(int i=lowerRangeOfId;i<upperRangeOfId+1;i++)
        //             Ids.add(i);
        //     }
        // }
        // else if(inputIds.contains(",")){
        //     ArrayOfIds = inputIds.split(","); 
        //     for(String s:ArrayOfIds)
        //         Ids.add(Integer.parseInt(s));
        // }
        // else{
        //     Ids.add(Integer.parseInt(inputIds));
        // }
        // ArrayList<BooksEntity> booksToAdd=new ArrayList<BooksEntity>();
        // ArrayList<Integer> idsNotAdded=new ArrayList<>();
        // Boolean boolean2=false;
        // for(Integer id:Ids){
        //     if(booksEntityRepo.getBookIds().contains(id)){
        //         boolean2=openWindow.openConfirmation("Warning", "You have entered an Id: "+id+" which is already in records. Do you want to ignore the Id and add other Ids or edit the Id to add into database?  Choose Yes to edit or No to ignore." );
        //         if(boolean2){
        //             idsNotAdded.add(id);
        //             Ids.remove(id);
        //         }
        //         else{
        //             inputOfBookIds.requestFocus();
        //             break;
        //         }
        //     }
        //     else if(boolForValidRange){
        //         openWindow.openDialogue("Warning", "You have entered invalid range of Ids.");
        //         inputOfBookIds.requestFocus();
        //         break;
        //     }
        //     else{
        //         BooksEntity booksEntity=new BooksEntity();
        //         booksEntity.setBookId(id);
        //         booksEntity.setStatus("Available");
        //         booksEntity.setDateOfAllotment(null);
        //         booksEntity.setBookDetailsEntity(bookDetailsEntity);
        //         booksToAdd.add(booksEntity);
        //     }
        // }
        // if(boolean2){
        //     openWindow.openDialogue("Info", "The id's: "+Ids+" are successfully being able to add into database within "+bookCode.getText()+" Book code.\nThe Ids "+idsNotAdded+" are not being able to be added into database as there exists books with Ids given. Do you want to proceed?\n\n\nNote: You can view the added books with Book Ids through Available books option by searching with Book Ids or Book Code.");
        //     if(boolean1){
        //         bookEntityService.addBooks(booksToAdd);
        //         //bookCode.clear();
        //         inputOfBookIds.clear();
        //         //tableToShowBook.getItems().clear();
        //     }
        // }
        // else{
        //     inputOfBookIds.requestFocus();
        // }