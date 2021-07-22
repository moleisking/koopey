package com.koopey.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "advert")
public class Advert extends BaseEntity {

    private static final long serialVersionUID = 7523090550210573431L;
 

    @Column(name = "start")
    private long start;

    @Column(name = "end")
    private long end;
    
    @OneToOne
    private Asset asset;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    // @Override
    // public String toString() {
    //     return MoreObjects.toStringHelper(this).add("id", id).add("type", type).add("start", start).add("end", end)
    //             .add("type", type).add("publish", publishDate).toString();
    // }
}