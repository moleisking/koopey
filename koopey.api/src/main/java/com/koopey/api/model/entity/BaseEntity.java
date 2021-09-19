package com.koopey.api.model.entity;

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

    @Size(min = 3, max = 50)
    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Column(name = "type")
    protected String type;

    @Column(name = "timeZone")
    protected String timeZone = "Etc/UTC";

    @Builder.Default
    @Column(name = "publish_date")
    protected Long publishDate = System.currentTimeMillis() / 1000;

}
