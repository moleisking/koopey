package com.koopey.api.model.entity;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import lombok.Data;

@Data
public class CompetitionRaw {
    
    public UUID id = UUID.randomUUID();
    public String user_id ;
    public String game_id ;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("user_id", user_id).add("game_id", game_id).toString();
    }
}
