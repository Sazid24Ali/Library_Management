package com.lib.library_management;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;


@SpringBootApplication
@ComponentScan(basePackages = "com.lib.library_management")
public class LibraryManagementApplication {

	public static void main(String[] args) {
		// SpringApplication.run(LibraryManagementApplication.class, args);
		Application.launch(javafx_main.class,args);
	}

}
