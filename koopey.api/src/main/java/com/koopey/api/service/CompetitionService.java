package com.koopey.api.service;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.CompetitionRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends BaseService <Competition, UUID>{
    
    @Autowired
    CompetitionRepository competitionRepository;

    BaseRepository<Competition, UUID> getRepository() {       
        return competitionRepository;
    }

    public List<Game> getGames(UUID userId){

        List<Game> games  = competitionRepository.findGames(userId);
        
        return games;
    }

    public List<User> getPlayers(UUID gameId){

        List<User> users  = competitionRepository.findPlayers(gameId);
        
        return users;
    }

}
