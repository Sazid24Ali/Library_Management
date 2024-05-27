package com.lib.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lib.library_management.Entity.BooksEntity;

@Repository
public interface BooksRepo extends JpaRepository<BooksEntity, Integer> {

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode")
    long countTotalBooks(@Param("bookCode") Integer bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Available'")
    long countAvailableBooks(@Param("bookCode") Integer bookCode);

    @Query("SELECT COUNT(b) FROM BooksEntity b WHERE b.bookDetailsEntity.BookCode = :bookCode AND b.status = 'Borrowed'")
    long countBorrowedBooks(@Param("bookCode") Integer bookCode);

}
