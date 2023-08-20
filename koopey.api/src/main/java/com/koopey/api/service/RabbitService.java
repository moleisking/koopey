package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import com.koopey.api.service.base.BaseService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService extends BaseService<Message, UUID> {
    private final Connection rabbitMQConnection;

    @Autowired
    private CustomProperties customProperties;
    private final MessageRepository messageRepository;

    RabbitService(@Lazy MessageRepository messageRepository, @Lazy Connection rabbitMQConnection) {

        this.messageRepository = messageRepository;
        this.rabbitMQConnection = rabbitMQConnection;
    }

    @PostConstruct
    private void postConstruct() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(customProperties.getRabbitmqUser());
        factory.setPassword(customProperties.getEmailPassword());
        factory.setVirtualHost("/");
        factory.setHost(customProperties.getRabbitmqHost());
        factory.setPort(customProperties.getRabbitmqPort());

        try {
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();
        } catch (IOException e) {
            log.error("RabbitMQ IO error: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ Timeout error: {}", e.getMessage());
        }
    }

    @PreDestroy
    private void destroyConstruct() {
        try {
            Connection conn = factory.newConnection();

            Channel channel = conn.createChannel();

            channel.exchangeDeclare(customProperties.getRabbitmqExchange(), "direct", true);
            channel.queueDeclare(customProperties.getRabbitmqQueue(), true, false, false, null);
            channel.queueBind(customProperties.getRabbitmqQueue(), customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey());

            byte[] messageBodyBytes = "Hello, world!".getBytes();
            channel.basicPublish(customProperties.getRabbitmqExchange(), customProperties.getRabbitmqRouteKey(), null,
                    messageBodyBytes);

            channel.close();
            conn.close();
        } catch (IOException e) {
            log.error("RabbitMQ IO error: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ Timeout error: {}", e.getMessage());
        }
    }

    protected AuditRepository<Message, UUID> getRepository() {
        return messageRepository;
    }

    // public Long count() {
    // return 0;
    // }

    public void read(Message message) {

    }

    public void send(Message message) {

        try {
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            channel.exchangeDeclare(customProperties.getRabbitmqExchange(), "direct", true);
            channel.queueDeclare(customProperties.getRabbitmqQueue(), true, false, false, null);
            channel.queueBind(customProperties.getRabbitmqQueue(), customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey());

            byte[] messageBodyBytes = "Hello, world!".getBytes();
            channel.basicPublish(customProperties.getRabbitmqExchange(), customProperties.getRabbitmqRouteKey(), null,
                    messageBodyBytes);

            channel.close();
            conn.close();
        } catch (IOException e) {
            log.error("RabbitMQ IO error: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ Timeout error: {}", e.getMessage());
        }
    }
}
