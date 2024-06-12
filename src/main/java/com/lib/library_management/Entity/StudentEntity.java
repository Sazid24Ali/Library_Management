package com.lib.library_management.Entity;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StudentEntity {

    @Id
    String StudentRollNo;
    String StudentName;
    String PhoneNumber;
    String Branch;
    Year YearOfPassing;
    String facultyPosition;

    public StudentEntity() {
    }

    public StudentEntity(String studentRollNo, String studentName, String phoneNumber, String branch,
            Year yearOfPassing) {
        StudentRollNo = studentRollNo;
        StudentName = studentName;
        PhoneNumber = phoneNumber;
        Branch = branch;
        YearOfPassing = yearOfPassing;
    }

    @Override
    public String toString() {
        return "\nStudentRollNo=" + StudentRollNo + "\nStudentName=" + StudentName + "\nPhoneNumber="
                + PhoneNumber + "\nBranch=" + Branch + "\nYearOfPassing=" + YearOfPassing + "\n";
    }

}
