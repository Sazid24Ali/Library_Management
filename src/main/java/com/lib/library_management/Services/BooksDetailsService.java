package com.lib.library_management.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Repository.BookDetailsRepo;

@Service
public class BooksDetailsService {

    @Autowired
    BookDetailsRepo bookDetailsRepo;

    public List<BookDetailsEntity> getAllBooks() {
        return bookDetailsRepo.findAll();
    }

    public BookDetailsEntity getBookDetailsByBookCode(Integer bcode) {
        try {
            BookDetailsEntity bookDetailsEntity = bookDetailsRepo.findById(bcode).get();
            return bookDetailsEntity;
        } catch (Exception e) {

        }
        return null;
    }

}
