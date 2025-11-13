package com.exercise.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exercise.demo.entity.*;
import com.exercise.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@SpringBootApplication
public class SBexerciseApplication {

	public static void main(String[] args) {


        //Starts the embedded Tomcat server, Initializes Spring Boot, Spring MVC, Spring Data JPA
        SpringApplication.run(SBexerciseApplication.class, args);
	}

    //Runs immediately after the application starts
    @Bean
    public CommandLineRunner demo(EmployeeRepository employeeRepo, EmployeePhoneRepository phoneRepo) {

        return (args) ->  {

        };


}


}
