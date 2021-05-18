package com.koopey.api.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Data
@Table(name = "advert")
public class Advert implements Serializable {

    private static final long serialVersionUID = 7523090550210573431L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id ;

    @Column(name = "type")
    private String type;

    @Column(name = "start")
    private long start;

    @Column(name = "end")
    private long end;

    @Builder.Default
    @Column(name = "publish_date")
    private Long publishDate = System.currentTimeMillis() / 1000;
    
    @OneToOne
    private Asset asset;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("type", type).add("start", start).add("end", end)
                .add("type", type).add("publish", publishDate).toString();
    }
}