package com.etms.EmployeeTaskManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EmployeeTaskManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTaskManagementSystemApplication.class, args);
	}

}
