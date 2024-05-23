package com.lib.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.library_management.Entity.BookDetailsEntity;

@Repository
public interface BookDetailsRepo extends JpaRepository<BookDetailsEntity, Integer> {

}
