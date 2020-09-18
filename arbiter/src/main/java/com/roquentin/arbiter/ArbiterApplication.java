package com.roquentin.arbiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ArbiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbiterApplication.class, args);
	}

}
