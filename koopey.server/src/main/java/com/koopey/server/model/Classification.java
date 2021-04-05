package com.koopey.server.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import lombok.Data;

@Data
@Entity
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "publish_date")
    @JsonIgnore 
    private Long publishDate = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }
}
