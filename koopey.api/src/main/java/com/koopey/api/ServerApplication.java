package com.koopey.api;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaRepositories("com.koopey.api.repository")
public class ServerApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ServerApplication.class, args);
	}

}