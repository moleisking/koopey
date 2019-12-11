package com.koopey.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 7523090550210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "latitude")
    private long latitude;

    @Column(name = "longitude")
    private long longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "timestamp")
    private long timestamp = System.currentTimeMillis()/1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("latitude", latitude).add("longitude", longitude)
                .add("type", type).add("address", address).add("type", type).add("timestamp", timestamp).toString();
    }
}