package com.kh.slumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SlumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlumberApplication.class, args);
	}

}
