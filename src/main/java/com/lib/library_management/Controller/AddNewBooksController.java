package com.lib.library_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lib.library_management.Entity.BookDetailsEntity;

import com.lib.library_management.Services.BookDetailsService;
import com.lib.library_management.Utility.OpenWindow;
import com.lib.library_management.Utility.utilityClass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Component
public class AddNewBooksController {
    @Autowired
    private OpenWindow openWindow;
    @Autowired
    private BookDetailsService bookDetailsService;

    @FXML
    private Button save;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfbookcode;

    @FXML
    private TextField tfbookname;

    @FXML
    private TextField tfedition;

    @FXML
    private TextField tfpages;

    @FXML
    private TextField tfplaceandpublisher;

    @FXML
    private TextField tfprice;

    @FXML
    private TextField tfsubjectcategory;

    @FXML
    private TextField tfyear;

    @FXML
    private TextField tfisbnNo;

    @FXML
    private TextField tfcallNo;

    @FXML
    void initialize() {
        // utilityClass.setIntegerLimiter(tfAuthor, 50);
        utilityClass.setIntegerLimiter(tfbookcode, 5);
        utilityClass.setIntegerLimiter(tfyear, 4);
        utilityClass.setIntegerLimiter(tfprice, 10000);
        utilityClass.setIntegerLimiter(tfpages, 10000);
        // utilityClass.setIntegerLimiter(tfsubjectcategory, 50);
    }

    @FXML
    void onclicksave(ActionEvent event) {
        try {
            // Integer BookCode = Integer.parseInt(tfbookcode.getText());
            String Author = tfAuthor.getText();
            String BookName = tfbookname.getText();
            String Edition = tfedition.getText();
            Integer pages = Integer.parseInt(tfpages.getText());
            Integer publishing_year = Integer.parseInt(tfyear.getText());
            Integer price = Integer.parseInt(tfprice.getText());
            String place_publisher = tfplaceandpublisher.getText();
            String SubjectCategory = tfsubjectcategory.getText();
            String IsbnNo = tfisbnNo.getText();
            String callNo = tfcallNo.getText();
            tfbookcode.clear();
            tfAuthor.clear();
            tfbookname.clear();
            tfedition.clear();
            tfpages.clear();
            tfplaceandpublisher.clear();
            tfprice.clear();
            tfsubjectcategory.clear();
            tfyear.clear();
            tfisbnNo.clear();
            tfcallNo.clear();
            // if (bookDetailsService.checkBookCodeIsExist(BookCode)) {
            // openWindow.openDialogue("Warning", " BookCode is Already Exist ");
            // } else {
            BookDetailsEntity newBookData = new BookDetailsEntity(price, BookName, Author, SubjectCategory,
                    Edition, pages, place_publisher, publishing_year, price, IsbnNo, callNo);
            if (openWindow.openConfirmation("Add New Book?", "Do you want save the book data?")) {
                System.out.println(newBookData);
                Integer bookCode = bookDetailsService.addBooksData(newBookData);
                if (bookCode == -1) {

                    openWindow.openDialogue("Error", "Failed to Add Book Data");

                } else {
                    openWindow.openDialogue("Successful:",
                            "Successfully Added the Book with the BookName \" " + BookName + " \" and BookCode \" "
                                    + bookCode + " \" ");
                    // Clear fields or perform any other necessary actions
                    // }
                }
            }
        } catch (NumberFormatException e) {
            openWindow.openDialogue("Error", "Invalid Input");
        }

    }
}
