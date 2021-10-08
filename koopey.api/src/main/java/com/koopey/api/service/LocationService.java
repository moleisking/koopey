package com.koopey.api.service;

import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.LocationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends BaseService <Location, UUID> {
   
    @Autowired
    LocationRepository locationRepository;

    BaseRepository<Location, UUID> getRepository() {       
        return locationRepository;
    }

    public List<Location> findByOwnerId(UUID ownerId) {
		return locationRepository.findByOwnerId(ownerId);
	}

}
