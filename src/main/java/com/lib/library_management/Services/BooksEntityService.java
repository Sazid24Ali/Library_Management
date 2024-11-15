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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksEntityService {

    @Autowired
    BooksEntityRepo booksRepo;

    @Autowired
    BookDetailsRepo bookDetailsRepo;

    @Transactional
    public void deleteBookDetailsByCode(Long bookCode) {
        List<BooksEntity> booksToDelete = getBooksByCode(bookCode);
        if (!booksToDelete.isEmpty()){    
            for (BooksEntity book : booksToDelete) {
                deleteBook(book.getBookId());
            }
        }
        bookDetailsRepo.deleteById(bookCode);

    }

    BooksEntity booksEntity;

    public Integer getAllBooksCount(){
        return booksRepo.countAllBooks();
    }

    public Integer getAllAvailableBooksCount(){
        return booksRepo.countAllAvailableBooks();
    }

    public long countTotalBooks(Long bookCode) {
        return booksRepo.countTotalBooks(bookCode);
    }

    public long countAvailableBooks(Long bookCode) {
        return booksRepo.countAvailableBooks(bookCode);
    }

    public long countBorrowedBooks(Long bookCode) {
        return booksRepo.countBorrowedBooks(bookCode);
    }

    public ArrayList<String> getBookIds(Long bookCode) {
        return booksRepo.getBookIds(bookCode);
    }

    public ArrayList<String> getBorrowedStudents(Long bookCode) {
        ArrayList<String> data = booksRepo.getBorrowedStudents(bookCode);
        ArrayList<String> formattedData = new ArrayList<>();
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

    public BooksEntity getBookDataByBookId(String bookId) {
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

    public boolean checkBookExistsById(String bookId) {
        return booksRepo.existsById(bookId);
    }

    public boolean isBookAlreadyBorrowed(String bookId) {
        BooksEntity book = booksRepo.findById(bookId).orElse(null);
        return book != null && book.getStatus().equals("Borrowed");
    }

    public void deleteBook(String bookId) {
        // Changed the argument from Integer
        booksRepo.deleteById(bookId);
    }

    public void saveOrUpdateBooks(ObservableList<BooksEntity> observableBookList) {
        List<BooksEntity> booksToSaveOrUpdate = new ArrayList<>(observableBookList);
        booksRepo.saveAll(booksToSaveOrUpdate);
    }

    public String getBorrowerRollNo(String bookId) {

        Optional<BooksEntity> bookOptional = booksRepo.findById(bookId);
        return bookOptional.map(book -> {
            StudentEntity student = book.getStudent();
            return (student != null) ? student.getStudentRollNo() : null;
        }).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<BookDetailsEntity> getBookDetailsByCode(Long bookCode) {
        List<BooksEntity> booksEntities = booksRepo.findByBookCode(bookCode);
        return booksEntities.stream()
                .map(BooksEntity::getBookDetailsEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BooksEntity> getBooksByCode(Long bookCode) {
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

    public BooksEntity getBookById(String bookId) {
        return booksRepo.findBookByBookId(bookId);
    }

    public String getCallNO(Long bookCode) {
        return booksRepo.findCall_noByBookCode(bookCode);
    }

    public String getISBN_no(Long bookCode) {
        return booksRepo.findISBN_noByBookCode(bookCode);
    }

    public String getremarks(Long bookCode) {
        return booksRepo.findRemarksByBookCode(bookCode);
    }

}
