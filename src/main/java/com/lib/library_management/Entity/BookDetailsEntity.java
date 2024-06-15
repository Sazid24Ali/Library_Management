package com.lib.library_management.Entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class BookDetailsEntity {

    @Id
    Integer BookCode;// Change it to Sting If Needed
    String BookName;
    String Author;
    String SubjectCategory;
    String Edition;
    Integer pages;
    String place_publisher;
    Integer publishing_year;
    Integer price;

    public BookDetailsEntity() {

    }

    public BookDetailsEntity(Integer bookCode, String bookName, String author, String subjectCategory, String edition,
            Integer pages, String place_publisher, Integer publishing_year, Integer price) {
        BookCode = bookCode;
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
