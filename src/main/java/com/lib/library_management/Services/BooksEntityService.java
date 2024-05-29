package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Repository.BooksEntityRepo;
import com.lib.library_management.Entity.BooksEntity;

import java.util.ArrayList;

@Service
public class BooksEntityService {

    @Autowired
    BooksEntityRepo booksRepo;

    BooksEntity booksEntity;

    public void addBooks(ArrayList<BooksEntity> booksToAdd){
        booksRepo.saveAll(booksToAdd);
    }

}
