package com.koopey.model;

import com.koopey.model.base.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Game extends Base {
    public static final String GAME_FILE_NAME = "game.dat";

    String firstId;

    String secondId;

}
