package com.koopey.api.service;

import java.util.List;
import java.util.UUID;
import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends BaseService <Competition, UUID>{
    
    @Autowired
    CompetitionRepository competitionRepository;

    BaseRepository<Competition, UUID> getRepository() {       
        return competitionRepository;
    }

    public List<Game> getMyGames(UUID userId){

        List<Game> games  = competitionRepository.findMyGames(userId);
        
        return games;
    }

    public List<Game> getPlayers(UUID gameId){

        List<Game> games  = competitionRepository.findPlayers(gameId);
        
        return games;
    }

}
