package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import lombok.Data;

@Data
public class CompetitionDto implements Serializable {

    public UUID id = UUID.randomUUID();
    public String userId;
    public String gameId;
    public Game game;
    public User player;  
}
