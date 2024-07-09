package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public StudentEntity getStudentDataByRollNo(String RollNo) {
        try {
            StudentEntity studentEntity = studentRepo.findById(RollNo).get();
            return studentEntity;
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Element not found");
        }
        return null;

    }

    public boolean addStudentData(StudentEntity StudentData) {
        try {
            studentRepo.save(StudentData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public StudentEntity deleteById(String rollNo) {
        StudentEntity data = getStudentDataByRollNo(rollNo);
        try {
            studentRepo.deleteById(rollNo);
            return data;

        } catch (Exception e) {
            return null;
        }
    }

    public StudentEntity getStudentByRollNo(String rollNo) {

        try {
            StudentEntity data2 = studentRepo.findById(rollNo).get();
            return data2;
        } catch (Exception e) {
            return null;
        }
    }

}
