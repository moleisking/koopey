package com.koopey.api.service;

import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.GameRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService extends BaseService <Game, UUID>{
    
    @Autowired
    GameRepository gameRepository;

    BaseRepository<Game, UUID> getRepository() {       
        return gameRepository;
    }
}
