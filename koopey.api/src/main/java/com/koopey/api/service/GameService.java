package com.koopey.api.service;

import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.GameRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService extends AuditService <Game, UUID>{
    
    @Autowired
    GameRepository gameRepository;

    protected AuditRepository<Game, UUID> getRepository() {       
        return gameRepository;
    }
}
