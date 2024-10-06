package com.koopey.api.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SystemService {

    @PostConstruct
    private void init() {       
        try {
            InetAddress host = InetAddress.getLocalHost();
            log.info("IP_ADDRESS: {}", host.getHostAddress());
            log.info("HOST: {}", host.getHostName());
        } catch (UnknownHostException e) {
            log.info("IP_ADDRESS: {}", "UnknownHost");
        }
        log.info("KAFKA_HOST: {}", System.getenv("KAFKA_HOST"));
        log.info("MYSQL_HOST: {}", System.getenv("MYSQL_HOST"));
        log.info("MYSQL_USER: {}", System.getenv("MYSQL_USER"));
        log.info("MYSQL_PASSWORD: {}", System.getenv("MYSQL_PASSWORD"));
    }
}
