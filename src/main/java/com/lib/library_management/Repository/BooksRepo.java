package com.lib.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.library_management.Entity.BooksEntity;

@Repository
public interface BooksRepo extends JpaRepository<BooksEntity, Integer> {

}
