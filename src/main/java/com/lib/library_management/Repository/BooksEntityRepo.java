package com.lib.library_management.Repository;

import java.util.List;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lib.library_management.Entity.BooksEntity;

@Repository
public interface BooksEntityRepo extends JpaRepository<BooksEntity, Integer> {
    
    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    long countTotalBooks(@Param("bookCode") Integer bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Available'")
    long countAvailableBooks(@Param("bookCode") Integer bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Borrowed'")
    long countBorrowedBooks(@Param("bookCode") Integer bookCode);

    // @Query("SELECT b.BookId FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode
    // = :bookCode AND b.status = 'Available' ")
    // Add the uncommented above line if we want to only display the Available books
    // Ids
    // the Below Query display all the book ids
    @Query("SELECT b.BookId FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode ")
    ArrayList<String> getBookIds(Integer bookCode);

    @Query("SELECT b FROM BooksEntity b WHERE b.student.StudentRollNo = :StudentRollNo")
    List<BooksEntity> findBooksEntitiesByStudent_StudentRollNo(String StudentRollNo);

    @Query("SELECT b.BookId FROM BooksEntity b")
    ArrayList<Integer> getBookIds();

    
}
