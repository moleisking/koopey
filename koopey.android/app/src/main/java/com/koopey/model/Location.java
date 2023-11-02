package com.koopey.model;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.model.base.Base;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Location extends Base  {

    public static final String LOCATION_FILE_NAME = "location.dat";

    @Builder.Default
    private Double altitude = 0.0d;
    @Builder.Default
    private Double latitude = 0.0d;
    @Builder.Default
    private Double longitude = 0.0d;
    @Builder.Default
    private Double distance = 0.0d;
    public String place ;
    private String ownerId;

    public boolean isEmpty() {
        if ((this.getDescription() != null && this.getDescription().length() > 5) || (this.latitude != 0.0d) || (this.longitude != 0.0d)) {
            return false;
        } else {
            return true;
        }
    }

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

}
