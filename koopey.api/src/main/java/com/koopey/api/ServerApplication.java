package com.koopey.api;

import com.koopey.api.configuration.CustomProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
@EnableJpaRepositories("com.koopey.api.repository")
@EnableAutoConfiguration
@ComponentScan({"com.koopey.api.controller","com.koopey.api.configuration", "com.koopey.api.service"})
public class ServerApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ServerApplication.class, args);
	}

}