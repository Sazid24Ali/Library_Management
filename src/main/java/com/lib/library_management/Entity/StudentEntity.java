package com.lib.library_management.Entity;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StudentEntity {

    @Id
    Integer StudentRollNo;
    String StudentName;
    Integer PhoneNumber;
    String Branch;
    Year YearOfPassing;

    
}
