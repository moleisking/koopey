package com.koopey.api;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.koopey.api.configuration.properties.CustomProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.koopey.api.repository")
//@EnableConfigurationProperties(CustomProperties.class)
@ComponentScan({"com.koopey.api.controller","com.koopey.api.configuration", "com.koopey.api.service"})
public class ServerApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ServerApplication.class, args);
	}

}