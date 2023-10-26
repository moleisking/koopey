package com.koopey.api.model.entity.base;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Builder.Default
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , length=16)
    protected UUID id = UUID.randomUUID();

    @Size(min = 1, max = 100)
    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Size(min = 0, max = 50)
    @Column(name = "type")
    protected String type;

    @Builder.Default
    @Size(min = 0, max = 20)
    @Column(name = "timeZone")
    protected String timeZone = "UTC/GMT";

    @Builder.Default
    @Column(name = "createTimeStamp")
    protected Long createTimeStamp = System.currentTimeMillis();

    @Builder.Default
    @Column(name = "readTimeStamp")
    protected Long readTimeStamp = 0L;

    @Builder.Default
    @Column(name = "updateTimeStamp")
    protected Long updateTimeStamp = 0L;

    @Builder.Default
    @Column(name = "deleteTimeStamp")
    protected Long deleteTimeStamp = 0L;
  
}
