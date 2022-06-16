package com.example.employee.app.employeesonprojects;

import com.example.employee.app.employeesonprojects.service.CsvWorkRecordReaderImpl;
import com.example.employee.app.employeesonprojects.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeesOnProjectsApplication{


	public static void main(String[] args) {
		SpringApplication.run(EmployeesOnProjectsApplication.class, args);
	}

}
