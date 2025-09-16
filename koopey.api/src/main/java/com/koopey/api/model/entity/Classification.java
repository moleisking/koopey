package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.BaseEntity;

import java.sql.Types;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Classification extends BaseEntity {


    @Column(name = "asset_id", length = 36, nullable = false, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID assetId;

    @Column(name = "tag_id", length = 36, nullable = false, unique = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
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
