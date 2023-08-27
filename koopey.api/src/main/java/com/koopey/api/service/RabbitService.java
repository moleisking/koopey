package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import com.koopey.api.service.base.BaseService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService extends BaseService<Message, UUID> {

    private Connection rabbitmqConnection;
    private Channel rabbitmqChannel;

    @Autowired
    private CustomProperties customProperties;
    private final MessageRepository messageRepository;

    RabbitService(
            @Lazy Channel rabbitmqChannel,
            @Lazy Connection rabbitmqConnection,
            @Lazy MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.rabbitmqChannel = rabbitmqChannel ;
        this.rabbitmqConnection = rabbitmqConnection;
    }

    @PostConstruct
    private void postConstruct() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(customProperties.getRabbitmqUser());
        factory.setPassword(customProperties.getRabbitmqPassword());
        factory.setVirtualHost("/");
        factory.setHost(customProperties.getRabbitmqHost());
        factory.setPort(customProperties.getRabbitmqPort());

        log.info("RabbitMQ Configurations: user : {}, password : {}, host : {}, port : {}, exchange : {}, , queue : {}",
                customProperties.getRabbitmqUser(),
                customProperties.getRabbitmqPassword(),
                customProperties.getRabbitmqHost(),
                customProperties.getRabbitmqPort(),
                customProperties.getRabbitmqExchange(),
                customProperties.getRabbitmqQueue());

        try {
            rabbitmqConnection = factory.newConnection();
            rabbitmqChannel = rabbitmqConnection.createChannel();
          //  if (rabbitmqChannel.isOpen()) {
             //   log.info("RabbitMQ channel open");
                Message m = new Message();
                m.setDescription("Hello World!");
                send(m);
           // } else {
           //     log.warn("RabbitMQ channel closed");
          //  }
        } catch (IOException e) {
            log.error("RabbitMQ PostConstruct IO error: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ PostConstruct Timeout error: {}", e.getMessage());
        }
    }

    @PreDestroy
    private void destroyConstruct() {

        try {
            rabbitmqChannel.close();
            rabbitmqConnection.close();
        } catch (IOException e) {
            log.error("RabbitMQ IO destroyConstructerror: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ Timeout destroyConstruct error: {}", e.getMessage());
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

            rabbitmqChannel.exchangeDeclare(customProperties.getRabbitmqExchange(), "direct", true);
            rabbitmqChannel.queueDeclare(customProperties.getRabbitmqQueue(), true, false, false, null);
            rabbitmqChannel.queueBind(customProperties.getRabbitmqQueue(),
                    customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey());

            byte[] messageBodyBytes = "Hello, world!".getBytes();
            rabbitmqChannel.basicPublish(
                    customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey(),
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    messageBodyBytes);
            log.info("RabbitMQ send message : {}", "Hello, world!");
        } catch (IOException e) {
            log.error("RabbitMQ IO send() error: {}", e.getMessage());
            // } catch (TimeoutException e) {
            // log.error("RabbitMQ Timeout send() error: {}", e.getMessage());
        }
    }
}
