package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Repository.BooksEntityRepo;
import java.util.ArrayList;
import java.util.List;

@Service
public class BooksEntityService {

    @Autowired
    BooksEntityRepo booksRepo;

    BooksEntity booksEntity;

    public long countTotalBooks(Integer bookCode) {
        return booksRepo.countTotalBooks(bookCode);
    }

    public long countAvailableBooks(Integer bookCode) {
        return booksRepo.countAvailableBooks(bookCode);
    }

    public long countBorrowedBooks(Integer bookCode) {
        return booksRepo.countBorrowedBooks(bookCode);
    }

    public ArrayList<String> getBookIds(Integer bookCode) {
        return booksRepo.getBookIds(bookCode);
    }

    public ArrayList<String> getBorrowedStudents(Integer bookCode) {
        ArrayList<String> data = booksRepo.getBorrowedStudents(bookCode);
        ArrayList<String> formattedData = new ArrayList<>();
        for (int j = 0; j < data.size(); j++) {
            String pair = data.get(j).replace(",", " -> ");
            formattedData.add(pair);
        }
        // System.out.println(formattedData);
        return formattedData;

    }

    public List<BooksEntity> getBooksFromStudentRollNo(String RollNo) {
        return booksRepo.findBooksEntitiesByStudent_StudentRollNo(RollNo);
    }

    public void addBooks(ArrayList<BooksEntity> booksToAdd) {
        booksRepo.saveAll(booksToAdd);
    }

    public void saveReturningBook(BooksEntity booksEntity) {
        booksRepo.save(booksEntity);
    }

    public BooksEntity getBookDataByBookId(Integer bookId) {
        try {
            BooksEntity bookDetailsEntity = booksRepo.findById(bookId).get();
            return bookDetailsEntity;

        } catch (Exception e) {

        }
        return null;
    }

    public void updateBookStatus(BooksEntity book) {
        try {
            booksRepo.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BooksEntity> getAllBooks() {
        return booksRepo.findAll();
    }

    public boolean checkBookExistsById(Integer bookId) {
        return booksRepo.existsById(bookId);
    }

}