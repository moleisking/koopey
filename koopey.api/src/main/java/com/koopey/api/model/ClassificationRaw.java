package com.koopey.api.model;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import lombok.Data;

@Data
public class ClassificationRaw {
    
    public UUID id = UUID.randomUUID();
    public String asset_id ;
    public String tag_id ;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("asset_id", asset_id).add("tag_id", tag_id).toString();
    }
}
