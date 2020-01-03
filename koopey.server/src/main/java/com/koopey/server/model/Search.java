
package com.koopey.server.model;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Search implements Serializable {

    private static final long serialVersionUID = 7556090450218953431L;

    private String id;
    
    private String alias;

    private String name;
   
    private String type;  
   
    private Long start;
   
    private Long end;
    
    private Long max;
   
    private Long min;
  
    private Long latitude;
  
    private Long longitude;
   
    private Long radius;   

    private List<Tag> tags;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("alias", alias).add("name", name).add("type", type).add("start", start)
                .add("end", end).add("max", max).add("min", min).add("latitude", latitude).add("longitude", longitude).add("radius", radius)
                .add("tags", tags).toString();
    }
}