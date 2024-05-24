package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public StudentEntity getStudentDataByRollNo(Integer RollNo) {

        return studentRepo.findById(RollNo).get();
        // return null;

    }

}
