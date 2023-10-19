package com.koopey.model;

import com.koopey.model.base.BaseCollection;
import com.koopey.model.type.LocationType;

public class Locations  extends BaseCollection<Location> {

    public static final String LOCATIONS_FILE_NAME = "locations.dat";

    public Location getTypeDestination() {
        return this.findFirstByType(LocationType.Destination).orElse(null);
    }
    public Location getTypeInvoice() {
        return this.findFirstByType(LocationType.Invoice).orElse(null);
    }
    public Location getTypePosition() {
        return this.findFirstByType(LocationType.Position).orElse(null);
    }
    public Location getTypeResidence() {
        return this.findFirstByType(LocationType.Residence).orElse(null);
    }
    public Location getTypeSource() {
        return this.findFirstByType(LocationType.Source).orElse(null);
    }

}
