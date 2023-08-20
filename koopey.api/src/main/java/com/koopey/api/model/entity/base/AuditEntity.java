package com.koopey.api.model.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class AuditEntity extends BaseEntity {
    
    @Size(min = 1, max = 100)
    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Size(min = 1, max = 50)
    @Column(name = "type")
    protected String type;

    @Size(min = 0, max = 20)
    @Column(name = "timeZone")
    protected String timeZone = "UTC/GMT";
}
