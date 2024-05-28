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
    Integer BookCode;//Change it to Sting If Needed 

    String BookName;
    String Author;
    String SubjectCategory;
    String Edition;

    @Transient
    Long totalBooks;
    @Transient
    Long availableBooks;
    @Transient
    Long borrowedBooks;
    @Transient
    ArrayList<String> bookIds;


}
