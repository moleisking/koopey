package com.koopey.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koopey.api.model.entity.base.AuditEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true )
@NoArgsConstructor
@SuperBuilder
@Table(name = "advert")
public class Advert extends AuditEntity {

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;
    
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();

}