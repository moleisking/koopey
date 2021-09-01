package com.koopey.api.service;

import com.koopey.api.model.entity.Journey;
import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.JourneyRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyService extends BaseService <Journey, UUID> {
    
    @Autowired
    JourneyRepository journeyRepository;

    BaseRepository<Journey, UUID> getRepository() {       
        return journeyRepository;
    }

    public List<Location> getMyJourneys(UUID assetId){

        List<Location> locations  = journeyRepository.findAssetLocations(assetId);
        
        return locations;
    }

    public List<Location> getPassangers(UUID assetId){

        List<Location> locations  = journeyRepository.findPassangers(assetId);
        
        return locations;
    }

}
