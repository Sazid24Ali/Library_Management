package com.lib.library_management.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class BooksEntity {

    @Id
    Integer BookId;

    @ManyToOne
    @JoinColumn(name = "BookCode", nullable = false)
    BookDetailsEntity bookDetailsEntity;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "StudentRollNo")
    StudentEntity student;

    private LocalDate dateOfAllotment;

    @Transient
    String Edition;
    @Transient
    String BookName;

    // Other fields and methods

}
