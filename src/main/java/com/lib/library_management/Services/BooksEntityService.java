package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Repository.BooksEntityRepo;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksEntityService {

    @Autowired
    private BooksEntityRepo booksRepo;

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
        for (String datum : data) {
            String pair = datum.replace(",", " -> ");
            formattedData.add(pair);
        }
        return formattedData;
    }

    public List<BooksEntity> getBooksFromStudentRollNo(String rollNo) {
        return booksRepo.findBooksEntitiesByStudent_StudentRollNo(rollNo);
    }

    public void addBooks(ArrayList<BooksEntity> booksToAdd) {
        booksRepo.saveAll(booksToAdd);
    }

    public void saveReturningBook(BooksEntity booksEntity) {
        booksRepo.save(booksEntity);
    }

    public BooksEntity getBookDataByBookId(Integer bookId) {
        try {
            return booksRepo.findById(bookId).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<BooksEntity> getBooksByCode(Integer bookCode) {
        return booksRepo.findBooksByBookDetailsEntity_BookCode(bookCode);
    }

    @Transactional
    public boolean isBookAlreadyBorrowed(Integer bookId, String studentRollNo) {
        BooksEntity borrowedBook = booksRepo.findByBookIdAndStudentRollNoAndStatus(bookId, studentRollNo,
                "Borrowed");
        return borrowedBook != null;
    }

    public void deleteBook(Integer bookId) {
        booksRepo.deleteById(bookId);
    }

    public List<BooksEntity> getIssuedBooksByStudentRollNo(String rollNo) {
        return booksRepo.findByStudentRollNoAndStatus(rollNo, "Borrowed");
    }

    public void updateBook(BooksEntity book) {
        booksRepo.save(book);
    }

    @Transactional
    public void saveOrUpdateBook(BooksEntity book) {
        booksRepo.save(book);
    }

    public void saveOrUpdateBooks(List<BooksEntity> books) {
        booksRepo.saveAll(books);
    }
}
