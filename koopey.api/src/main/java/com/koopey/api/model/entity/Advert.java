package com.koopey.api.model.entity;

import com.koopey.api.model.entity.base.AuditEntity;
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
public class Advert extends AuditEntity {

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

}