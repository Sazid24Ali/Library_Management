package com.lib.library_management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdminEntity {

    @Id
    String UserName;
    String Password;
    
}
