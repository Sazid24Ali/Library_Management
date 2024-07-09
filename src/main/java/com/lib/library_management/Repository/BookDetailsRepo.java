package com.lib.library_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Entity.BooksEntity;

@Repository
public interface BookDetailsRepo extends JpaRepository<BookDetailsEntity, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM BookDetailsEntity b WHERE b.BookCode = :bookCode")
    void deleteByBookCode(Integer bookCode);

}
