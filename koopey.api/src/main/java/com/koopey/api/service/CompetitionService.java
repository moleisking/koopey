package com.koopey.api.service;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.CompetitionRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends BaseService<Competition, UUID> {

    @Autowired
    CompetitionRepository competitionRepository;

    BaseRepository<Competition, UUID> getRepository() {
        return competitionRepository;
    }

    public List<Game> findGames(UUID userId) {
        return competitionRepository.findGames(userId);
    }

    public List<User> findPlayers(UUID gameId) {
        return competitionRepository.findPlayers(gameId);
    }

}
