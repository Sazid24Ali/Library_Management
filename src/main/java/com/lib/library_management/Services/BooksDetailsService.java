package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Repository.BookDetailsRepo;

@Service
public class BooksDetailsService {

    @Autowired
    BookDetailsRepo bookDetailsRepo;
    

}
