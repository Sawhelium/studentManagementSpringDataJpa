package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.example.model")
public class ManageStudentSpringbootJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ManageStudentSpringbootJpaApplication.class, args);
	}

}
