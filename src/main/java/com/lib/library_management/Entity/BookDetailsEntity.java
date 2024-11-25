package com.lib.library_management.Entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class BookDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for H2 auto-increment
    Long BookCode;// Change it to Long
    String BookName;
    String Author;
    String SubjectCategory;
    String Edition;
    Integer pages;
    String place_publisher;
    Integer publishing_year;
    Integer price;
    String ISBN_no;
    String Call_no;
    String Remarks;

    public BookDetailsEntity() {

    }

    public BookDetailsEntity(String bookName, String author, String subjectCategory, String edition, Integer pages,
            String place_publisher, Integer publishing_year, Integer price, String iSBN_no, String call_no,
            String remarks) {
        BookName = bookName;
        Author = author;
        SubjectCategory = subjectCategory;
        Edition = edition;
        this.pages = pages;
        this.place_publisher = place_publisher;
        this.publishing_year = publishing_year;
        this.price = price;
        ISBN_no = iSBN_no;
        Call_no = call_no;
        Remarks = remarks;
    }

    public BookDetailsEntity(Long bookCode, String bookName, String author, String subjectCategory, String edition,
            Integer pages, String place_publisher, Integer publishing_year, Integer price, String ISBN_no,
            String Call_no, String Remarks) {
        BookCode = bookCode;
        BookName = bookName;
        Author = author;
        SubjectCategory = subjectCategory;
        Edition = edition;
        this.pages = pages;
        this.place_publisher = place_publisher;
        this.publishing_year = publishing_year;
        this.price = price;
        this.Call_no = Call_no;
        this.ISBN_no = ISBN_no;
        this.Remarks = Remarks;
    }

    public BookDetailsEntity(String bookName, String author, String subjectCategory, String edition, Integer pages,
            String place_publisher, Integer publishing_year, Integer price) {
        BookName = bookName;
        Author = author;
        SubjectCategory = subjectCategory;
        Edition = edition;
        this.pages = pages;
        this.place_publisher = place_publisher;
        this.publishing_year = publishing_year;
        this.price = price;
    }

    @Transient
    Long totalBooks;
    @Transient
    Long availableBooks;
    @Transient
    Long borrowedBooks;
    @Transient
    ArrayList<String> bookIds;
    @Transient
    ArrayList<String> borrowedStudents;

    @Override
    public String toString() {
        return "BookDetailsEntity [BookCode=" + BookCode + ", BookName=" + BookName + ", Author=" + Author
                + ", SubjectCategory=" + SubjectCategory + ", Edition=" + Edition + ", pages=" + pages
                + ", place_publisher=" + place_publisher + ", publishing_year=" + publishing_year + ", price=" + price
                + "]";
    }

}
