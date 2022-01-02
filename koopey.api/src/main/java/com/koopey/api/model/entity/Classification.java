package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Classification extends BaseEntity {

    @Column(name = "asset_id", length = 16, nullable = false, unique = false)
    protected UUID assetId;

    @Column(name = "tag_id", length = 16, nullable = false, unique = false)
    protected UUID tagId;

    @JsonIgnore()
    @JoinColumn(name = "asset_id", nullable = false, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    private Asset asset;

    @JsonIgnore()
    @JoinColumn(name = "tag_id", nullable = false, unique = true, insertable = false, updatable = false)
    @ManyToOne(targetEntity = Tag.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Tag tag;
}
