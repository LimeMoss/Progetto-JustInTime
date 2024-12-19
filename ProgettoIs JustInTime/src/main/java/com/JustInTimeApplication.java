package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.justInTime.controller")
@ComponentScan("com.justInTime.service")
public class JustInTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustInTimeApplication.class, args);
	}

}
