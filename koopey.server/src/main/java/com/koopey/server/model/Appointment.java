package com.koopey.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@Table(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 7523090550210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "sender")
    private String from;

    @Column(name = "receiver")
    private String to;

    @Column(name = "timestamp")
    private Long timestamp = System.currentTimeMillis() / 1000;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("from", from).add("to", to)
                .add("timestamp", timestamp).toString();
    }
}