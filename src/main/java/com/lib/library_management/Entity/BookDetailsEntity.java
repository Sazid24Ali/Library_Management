package com.lib.library_management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BookDetailsEntity {

    @Id
    Integer BookCode;//Change it to Sting If Needed 
    String BookName;
    String Author;
    String SubjectCategory;
    String Edition;


}
