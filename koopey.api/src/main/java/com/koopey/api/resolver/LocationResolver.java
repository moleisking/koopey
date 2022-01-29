package com.koopey.api.resolver;

//import graphql.kickstart.tools.GraphQLQueryResolver;
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.koopey.api.model.entity.Location;
import com.koopey.api.service.LocationService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DgsComponent
public class LocationResolver /*implements GraphQLQueryResolver*//*, GraphQLMutationResolver*/ {

    @Autowired
    private LocationService locationService;

    @DgsQuery
    public List<Location> getLocations(@InputArgument String name) {
        List<Location> location = locationService.findByName(name);
      log.info("Location size {}",  location.hashCode()) ;
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

      /* public DataFetcher getLocations() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("name");
            return locationService.findAll();
        };
    }*/
}
