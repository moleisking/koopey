package com.koopey.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "image")
public class Image extends BaseEntity {
    
    private static final long serialVersionUID = 7523097750210573431L;
  
    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "data")
    private String data;

    @JoinColumn(name = "asset_id", nullable = false)
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY, optional = false)
    private Asset asset;

}
