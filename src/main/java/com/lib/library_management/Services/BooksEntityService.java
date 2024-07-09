package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;
import com.lib.library_management.Entity.StudentEntity;
import com.lib.library_management.Repository.BookDetailsRepo;
import com.lib.library_management.Repository.BooksEntityRepo;

import javafx.collections.ObservableList;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksEntityService {

    @Autowired
    BooksEntityRepo booksRepo;
    @Autowired
    private BookDetailsRepo bookDetailsRepository;

    @Transactional
    public void deleteBookDetailsByCode(Integer bookCode) {
        bookDetailsRepository.deleteByBookCode(bookCode);
    }

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
        int i = 0;
        for (int j = 0; j < data.size(); j++) {
            String pair = data.get(j).replace(",", " -> ");
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

    public boolean isBookAlreadyBorrowed(Integer bookId) {
        BooksEntity book = booksRepo.findById(bookId).orElse(null);
        return book != null && book.getStatus().equals("Borrowed");
    }

    public void deleteBook(Integer bookId) {
        booksRepo.deleteById(bookId);
    }

    public void saveOrUpdateBooks(ObservableList<BooksEntity> observableBookList) {
        List<BooksEntity> booksToSaveOrUpdate = new ArrayList<>(observableBookList);
        booksRepo.saveAll(booksToSaveOrUpdate);
    }

    public String getBorrowerRollNo(Integer bookId) {

        Optional<BooksEntity> bookOptional = booksRepo.findById(bookId);
        return bookOptional.map(book -> {
            StudentEntity student = book.getStudent();
            return (student != null) ? student.getStudentRollNo() : null;
        }).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<BookDetailsEntity> getBookDetailsByCode(int bookCode) {
        return booksRepo.findByBookCode(bookCode);
    }

    @Transactional
    public List<BooksEntity> getBooksByCode(Integer bookCode) {
        List<BooksEntity> books = booksRepo.findBooksByBookCode(bookCode);

        // if (books.isEmpty()) {
        // // No books found in BooksEntity, check BookDetailsEntity
        // List<BooksEntity> bookDetails =
        // bookDetailsRepository.findByBookCode(bookCode);
        // if (bookDetails != null) {
        // BooksEntity book = new BooksEntity();
        // // Assuming bookDetails has a single book reference, adjust as per your
        // entity
        // // structure
        // BookDetailsEntity bookDetailsEntity=book.getBookDetailsEntity()
        // book.setBookId(book.getBookId()); // Assuming BookDetailsEntity has a
        // reference to BooksEntity
        // book.setBookCode(book.getBookCode());
        // book.setBookName(book.getBookName());
        // book.setAuthor(book.getAuthor());
        // book.setEdition(book.getEdition());
        // book.setSubjectCategory(book.getSubjectCategory());
        // books.add(book);
        // }
        // }

        return books;
    }

    public BooksEntity getBookById(Integer bookId) {
        return booksRepo.findBookByBookId(bookId);
    }

}
