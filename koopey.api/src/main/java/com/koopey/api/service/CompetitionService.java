package com.koopey.api.service;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import com.koopey.api.repository.CompetitionRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends BaseService<Competition, UUID> {

    private final CompetitionRepository competitionRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    CompetitionService(@Lazy CompetitionRepository competitionRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.competitionRepository = competitionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    protected BaseRepository<Competition, UUID> getRepository() {
        return competitionRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public List<Game> findGames(UUID userId) {
        return competitionRepository.findGames(userId);
    }

    public List<User> findPlayers(UUID gameId) {
        return competitionRepository.findPlayers(gameId);
    }

}
