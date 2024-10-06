package com.koopey.api.service;

import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.GameRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService extends BaseService<Game, UUID> {

    private final GameRepository gameRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    GameService(KafkaTemplate<String, String> kafkaTemplate,
            @Lazy GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    protected BaseRepository<Game, UUID> getRepository() {
        return gameRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public Long countByPlayer(UUID userId) {
        return gameRepository.countByPlayer(userId);
    }

    public List<Game> findByPlayer(UUID userId) {
        return gameRepository.findByPlayer(userId);
    }
}
