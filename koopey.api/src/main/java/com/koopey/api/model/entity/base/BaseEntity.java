package com.koopey.api.model.entity.base;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id" , length=36, columnDefinition = "VARCHAR(36)", unique = true,  nullable = false)
    protected UUID id; // = UUID.randomUUID();

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

    //boolean isInstance
  
}
