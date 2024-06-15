package com.lib.library_management.Entity;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StudentEntity {

    @Id
    String StudentRollNo; // Faculty CE ID
    String StudentName; // Faculty Name
    String PhoneNumber; // Faulty Phone Number
    String Branch; // "Faculty"
    Year YearOfPassing; // Null
    String facultyPosition;

    public StudentEntity() {
    }

    public StudentEntity(String studentRollNo, String studentName, String phoneNumber, String branch,
            Year yearOfPassing, String facultyposition) {
        StudentRollNo = studentRollNo;
        StudentName = studentName;
        PhoneNumber = phoneNumber;
        Branch = branch;
        YearOfPassing = yearOfPassing;
        facultyPosition = facultyposition;
    }

    @Override
    public String toString() {
        return "\nStudentRollNo=" + StudentRollNo + "\nStudentName=" + StudentName + "\nPhoneNumber="
                + PhoneNumber + "\nBranch=" + Branch + "\nYearOfPassing=" + YearOfPassing + "\n" + facultyPosition
                + "\n";
    }

}
