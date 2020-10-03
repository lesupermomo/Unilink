package com.unilink.backend.unilink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories(basePackages = "com.unilink.backend.unilink.repository"	)
@SpringBootApplication
public class UnilinkApplication {
		
	public static void main(String[] args) {
		
		SpringApplication.run(UnilinkApplication.class, args);		
		
	}
	
	@RequestMapping("/")
	public String greeting() {
		return "Welcome to Unilink, we are still in development:/ .\n"
				+ "Use the API methods to interact with the backend!!!";
	}
	
}
