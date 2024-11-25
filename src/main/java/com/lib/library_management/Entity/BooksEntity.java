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
    String BookId; // Changed from Integer

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
    String BookName;
    @Transient
    String Edition;
    @Transient
    String SubjectCategory;
    @Transient
    Long BookCode;
    @Transient
    String Author;
    @Transient
    String StudentRollNo;

    @Override
    public String toString() {
        return "BooksEntity{" +
                "BookId=" + BookId +
                ", BookName='" + BookName + '\'' +
                ", Edition='" + Edition + '\'' +
                ", Author='" + Author + '\'' +
                ", SubjectCategory='" + SubjectCategory + '\'' +
                ", BookCode=" + BookCode +
                ", status='" + status + '\'' +
                ", dateOfAllotment=" + dateOfAllotment +
                ", studentRollNo=" + (student != null ? student.getStudentRollNo() : "null") +
                '}';
    }

}
