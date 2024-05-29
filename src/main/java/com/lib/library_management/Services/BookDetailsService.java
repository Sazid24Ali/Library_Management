package com.lib.library_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Entity.BookDetailsEntity;
import com.lib.library_management.Repository.BookDetailsRepo;

@Service
public class BookDetailsService {

    @Autowired
    private BookDetailsRepo bookDetailsRepository;

    public List<BookDetailsEntity> getAllBooks() {
        return bookDetailsRepository.findAll();
    }

}
