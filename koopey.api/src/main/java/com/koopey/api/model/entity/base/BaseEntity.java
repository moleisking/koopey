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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , length=16)
    protected UUID id;

    @Size(min = 1, max = 100)
    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Size(min = 1, max = 50)
    @Column(name = "type")
    protected String type;

    @Builder.Default
    @Size(min = 0, max = 20)
    @Column(name = "timeZone")
    protected String timeZone = "UTC/GMT";

    @Builder.Default
    @Column(name = "createTimeStamp")
    public long createTimeStamp = 0;

    @Builder.Default
    @Column(name = "readTimeStamp")
    public long readTimeStamp = 0;

    @Builder.Default
    @Column(name = "updateTimeStamp")
    public long updateTimeStamp = 0;

    @Builder.Default
    @Column(name = "deleteTimeStamp")
    public long deleteTimeStamp = 0;
  
}
