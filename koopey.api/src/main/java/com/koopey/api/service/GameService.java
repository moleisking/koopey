package com.koopey.api.service;

import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.GameRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService extends AuditService<Game, UUID> {

    private final GameRepository gameRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    GameService(KafkaTemplate<String, String> kafkaTemplate,
            @Lazy GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    protected AuditRepository<Game, UUID> getRepository() {
        return gameRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }
}
