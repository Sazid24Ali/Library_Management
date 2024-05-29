package com.lib.library_management.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Repository.BooksRepo;

@Service
public class BooksService {

    @Autowired
    BooksRepo booksRepository;

    public long countTotalBooks(Integer bookCode) {
        return booksRepository.countTotalBooks(bookCode);
    }

    public long countAvailableBooks(Integer bookCode) {
        return booksRepository.countAvailableBooks(bookCode);
    }

    public long countBorrowedBooks(Integer bookCode) {
        return booksRepository.countBorrowedBooks(bookCode);
    }

    public ArrayList<String> getBookIds(Integer bookCode) {
        return booksRepository.getBookIds(bookCode);
    }

    public List<BooksEntity> getBooksFromStudentRollNo(String RollNo){
        return booksRepository.findBooksEntitiesByStudent_StudentRollNo(RollNo);
        // booksRepository.f
    }

}
