package com.lib.library_management.Repository;

import java.util.List;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;

@Repository
public interface BooksEntityRepo extends JpaRepository<BooksEntity, String> {

    @Query("SELECT COUNT(b) FROM BooksEntity b")
    Integer countAllBooks();

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.status = 'Available'")
    Integer countAllAvailableBooks();
    
    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    long countTotalBooks(@Param("bookCode") Long bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Available'")
    long countAvailableBooks(@Param("bookCode") Long bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Borrowed'")
    long countBorrowedBooks(@Param("bookCode") Long bookCode);

    // @Query("SELECT b.BookId FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode
    // = :bookCode AND b.status = 'Available' ")
    // Add the uncommented above line if we want to only display the Available books
    // Ids
    // the Below Query display all the book ids
    @Query("SELECT b.BookId FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode ")
    ArrayList<String> getBookIds(Long bookCode);

    @Query("SELECT b FROM BooksEntity b WHERE b.student.StudentRollNo = :StudentRollNo")
    List<BooksEntity> findBooksEntitiesByStudent_StudentRollNo(String StudentRollNo);

    @Query("SELECT b.BookId FROM BooksEntity b")
    ArrayList<String> getBookIds();

    @Query("SELECT b.student.StudentRollNo,b.BookId FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode and b.student.StudentRollNo IS NOT NULL")
    ArrayList<String> getBorrowedStudents(Long bookCode);

    @Query("SELECT b.bookDetailsEntity FROM BooksEntity b WHERE b.BookId = :bookId")
    BookDetailsEntity findBookDetailsByBookId(@Param("bookId") String bookId);

    @Query("SELECT b FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    List<BooksEntity> findBooksByBookDetailsEntity_BookCode(Long bookCode);

    @Query("SELECT bd FROM BookDetailsEntity bd WHERE bd.BookCode = :bookCode")
    List<BooksEntity> findByBookCode(@Param("bookCode") Long bookCode);

    @Query("SELECT bd.ISBN_no FROM BookDetailsEntity bd WHERE bd.BookCode = :bookCode")
    String findISBN_noByBookCode(Long bookCode);

    @Query("SELECT bd.Call_no FROM BookDetailsEntity bd WHERE bd.BookCode = :bookCode")
    String findCall_noByBookCode(Long bookCode);

    @Modifying
    @Transactional
    @Query("DELETE FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    void deleteBooksByBookCode(Long bookCode);

    @Query("SELECT b FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    List<BooksEntity> findBooksByBookCode(Long bookCode);

    @Query("SELECT b FROM BooksEntity b LEFT JOIN FETCH b.bookDetailsEntity bd WHERE b.BookId = :bookId")
    BooksEntity findBookByBookId(@Param("bookId") String bookId);

    @Query("SELECT bd.Remarks FROM BookDetailsEntity bd WHERE bd.BookCode = :bookCode")
    String findRemarksByBookCode(Long bookCode);

}
