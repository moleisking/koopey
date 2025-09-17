package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CompetitionDto extends BaseDto {

    public String userId;
    public String gameId;
    public Game game;
    public User player;  
}
