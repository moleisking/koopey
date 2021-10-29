package com.koopey.api.model.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class CompetitionDto implements Serializable {

    public UUID id = UUID.randomUUID();
    public String userId;
    public String gameId;

}
