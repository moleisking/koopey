package com.koopey.api.model.entity;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Search implements Serializable {

    private static final long serialVersionUID = 7556090450218953431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;

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
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("name", name)
                .add("type", type).add("start", start).add("end", end).add("max", max).add("min", min)
                .add("latitude", latitude).add("longitude", longitude).add("radius", radius).add("tags", tags)
                .toString();
    }
}