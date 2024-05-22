package com.lib.library_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.library_management.Repository.AdminRepo;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;
    
    
}
