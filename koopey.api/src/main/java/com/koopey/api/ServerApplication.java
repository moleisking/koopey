package com.koopey.api;

import javax.annotation.PostConstruct;

import com.koopey.api.configuration.CustomProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@EnableJpaRepositories("com.koopey.api.repository")
@EnableAutoConfiguration
@ComponentScan({"com.koopey.api.controller","com.koopey.api.configuration", "com.koopey.api.service"})
public class ServerApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ServerApplication.class, args);
	}

	@Autowired
    private CustomProperties customProperties;

	@PostConstruct
    public void display()
    {
        System.out.println("Google:" + customProperties.getGoogleApiKey());
		System.out.println("Xxx:" + customProperties.getXxx());
	}

}