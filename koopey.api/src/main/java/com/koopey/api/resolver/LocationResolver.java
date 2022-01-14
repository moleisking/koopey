package com.koopey.api.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.koopey.api.model.entity.Location;
import com.koopey.api.service.LocationService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocationResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private LocationService locationService;

    public List<Location> getLocations() {
        List<Location> location = locationService.findAll();
      log.info("Location size {}",  location.size()) ;
        return location;
    }

    public Location setLocation(BigDecimal altitude, String description,  BigDecimal latitude, BigDecimal longitude, String name, String place, String type) {
        Location location = new Location();
        location.setAltitude(altitude);
        location.setDescription(description);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setName(name);
        location.setPlace(place);
        location.setType(type);       
        return locationService.save(location);
    }
}
