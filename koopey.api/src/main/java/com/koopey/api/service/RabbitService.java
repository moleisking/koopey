package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.Message;
import com.koopey.api.repository.MessageRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.BaseService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.Queue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService extends BaseService<Message, UUID> {

    private Connection connection;
    private Channel channel;

    @Autowired
    private CustomProperties customProperties;
    private final MessageRepository messageRepository;

    RabbitService(
            @Lazy Channel channel,
            @Lazy Connection connection,
            @Lazy MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.channel = channel;
        this.connection = connection;
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
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            log.error("RabbitMQ PostConstruct IO error: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ PostConstruct Timeout error: {}", e.getMessage());
        }
    }

    @PreDestroy
    private void destroyConstruct() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            log.error("RabbitMQ IO destroyConstructerror: {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("RabbitMQ Timeout destroyConstruct error: {}", e.getMessage());
        }
    }

    protected AuditRepository<Message, UUID> getRepository() {
        return messageRepository;
    }

    public long count() {
        long count = 0;
        try {
            Queue.DeclareOk response = channel.queueDeclarePassive(customProperties.getRabbitmqQueue());
            count = response.getMessageCount();
        } catch (IOException e) {
            log.error("RabbitMQ IO setup() error: {}", e.getMessage());
        }
        return count;
    }

    public void send(Message message) {
        try {
            channel.exchangeDeclare(customProperties.getRabbitmqExchange(), BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(customProperties.getRabbitmqQueue(), true, false, false, null);
            channel.queueBind(customProperties.getRabbitmqQueue(),
                    customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey());

            channel.basicPublish(
                    customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey(),
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getDescription().getBytes());
            log.info("RabbitMQ send senderID : {}", message.getSenderId());
        } catch (IOException e) {
            log.error("RabbitMQ IO send() error: {}, message: {}", e.getMessage(), message.toString());
        }
    }

    public void startPush(UUID receiverId) {
        try {
            log.info("RabbitMQ send message : {}", "Hello, world!");

            boolean autoAck = false;
            channel.basicConsume(customProperties.getRabbitmqQueue(), autoAck, receiverId.toString(),
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                Envelope envelope,
                                AMQP.BasicProperties properties,
                                byte[] body)
                                throws IOException {
                            long deliveryTag = envelope.getDeliveryTag();

                            String message = new String(body, "UTF-8");
                            log.info("RabbitMQ read message : {}", message);
                            channel.basicAck(deliveryTag, false);
                        }
                    });
        } catch (IOException e) {
            log.error("RabbitMQ IO send() error: {}", e.getMessage());
        }
    }

    public List<Message> pole(UUID receiverId) {
        List<Message> messages = new ArrayList<Message>();
        try {
            boolean autoAck = false;
            GetResponse response = channel.basicGet(customProperties.getRabbitmqQueue(), autoAck);
            if (response == null) {
                log.info("RabbitMQ pole empty messages.");
            } else {
                AMQP.BasicProperties props = response.getProps();
                byte[] body = response.getBody();
                log.info("RabbitMQ messages. {}", body.toString());
                long deliveryTag = response.getEnvelope().getDeliveryTag();
                //channel.basicAck(deliveryTag, false);
            }
        } catch (IOException e) {
            log.error("RabbitMQ IO send() error: {}", e.getMessage());
        }
        return messages;
    }

    public void delete(UUID senderId) {
        try {

            channel.exchangeDeclare(customProperties.getRabbitmqExchange(), BuiltinExchangeType.DIRECT, true);
            channel.queueDelete(customProperties.getRabbitmqQueue(), false, false);
            channel.queueBind(customProperties.getRabbitmqQueue(),
                    customProperties.getRabbitmqExchange(),
                    customProperties.getRabbitmqRouteKey());

            log.info("RabbitMQ send message : {}", "Hello, world!");
        } catch (IOException e) {
            log.error("RabbitMQ IO send() error: {}", e.getMessage());
        }
    }
}
