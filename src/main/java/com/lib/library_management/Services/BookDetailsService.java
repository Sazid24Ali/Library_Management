package com.lib.library_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Repository.BookDetailsRepo;

@Service
public class BookDetailsService {

    @Autowired
    private final BookDetailsRepo bookDetailsRepository;

    public BookDetailsService(BookDetailsRepo bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public List<BookDetailsEntity> getAllBooks() {
        return bookDetailsRepository.findAll();
    }

    public boolean addBooksData(BookDetailsEntity BooksData) {
        try {
            bookDetailsRepository.save(BooksData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public BookDetailsEntity getBookDetailsEntity(Integer bookId) {
        return bookDetailsRepository.findById(bookId).get();
    }
}