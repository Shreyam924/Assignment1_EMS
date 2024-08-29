package com.springeboot.example.ems.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@SpringBootApplication
public class EmsBackendApplication {
	public static void main(String[] args) {

		SpringApplication.run(EmsBackendApplication.class, args);
	}

}

