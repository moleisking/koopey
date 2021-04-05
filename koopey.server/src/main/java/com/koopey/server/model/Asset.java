package com.koopey.server.model;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Entity
@Data
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 7523090550210693431L;
    
    @Id
    @GeneratedValue(generator = "uuid") // system-uuid
    @GenericGenerator(name = "uuid", strategy = "uuid2") // system-uuid
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "currency")
    private String currency;

    @Column(name = "type")
    private String type;

    @Column(name = "dimension_unit")
    private String dimensionUnit;

    @Column(name = "distance")
    private int distance;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "length")
    private int length;

    @Column(name = "value")
    private int value;

    @Column(name = "weight")
    private int weight;

    @Column(name = "weight_unit")
    private String weightUnit;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacturer_serial")
    private String manufacturer_serial;

    @Column(name = "manufacture_date")
    private long manufactureDate;

    @Column(name = "time_zone")
    private long timeZone;

    @Builder.Default
    @Column(name = "timestamp")
    private Long timestamp = System.currentTimeMillis()/1000;

    @Column(name = "available")
    private boolean available;   

     @ManyToMany
    private Set<User> users;

    @ManyToMany
    private Set<Tag> tags;
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("distance", distance).add("weight", weight)
                .add("height", height).add("length", length).add("width", width).add("value", value)
                .add("currency", currency).add("description", description).add("dimensionUnit", dimensionUnit)
                .add("weightUnit", weightUnit).add("description", description).add("type", type)
                .add("manufactureDate", manufactureDate).add("timeZone", timeZone).add("timestamp", timestamp)
                .add("users", users.toString()).add("tags", tags.toString()).toString();
    }
}