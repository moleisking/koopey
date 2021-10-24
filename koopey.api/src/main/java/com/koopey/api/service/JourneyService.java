package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Journey;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
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

    public List<User> findDrivers(UUID driverId){

        List<User> drivers  = journeyRepository.findDrivers(driverId);        
        return drivers;
    }

    public List<Journey> findDriver(UUID userId){

        List<Journey> journeys  = journeyRepository.findByDriverId(userId);        
        return journeys;
    }

    public List<Journey> findDestination(UUID locationId){

        List<Journey> journeys  = journeyRepository.findByDestinationId(locationId);        
        return journeys;
    }

    public List<Location> findDestinations(UUID assetId){

        List<Location> locations  = journeyRepository.findDestinations(assetId);        
        return locations;
    } 

    public List<Journey> findSource(UUID locationId){

        List<Journey> journeys  = journeyRepository.findBySourceId(locationId);        
        return journeys;
    }

    public List<Location> findSources(UUID assetId){

        List<Location> locations  = journeyRepository.findSources(assetId);        
        return locations;
    } 

    public List<User> findPassangers(UUID passangerId){

        List<User> passangers  = journeyRepository.findPassangers(passangerId);        
        return passangers;
    }

    public List<Journey> findPassanger(UUID userId){

        List<Journey> journeys  = journeyRepository.findByPassangerId(userId);        
        return journeys;
    }
  
    public List<Journey> findVehicle(UUID vehicleId){

        List<Journey> journeys  = journeyRepository.findByVehicleId(vehicleId);        
        return journeys;
    }

    public List<Asset> findVehicles(UUID vehicleId){

        List<Asset> assets  = journeyRepository.findVehicles(vehicleId);        
        return assets;
    }

}
