package com.koopey.api.service;

import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.LocationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends BaseService<Location, UUID> {

  @Autowired
  LocationRepository locationRepository;

  BaseRepository<Location, UUID> getRepository() {
    return locationRepository;
  }

  public List<Location> findByAreaAsKilometer(BigDecimal latitude, BigDecimal longitude, Integer radius) {
    return locationRepository.findByAreaAsKilometer(latitude, longitude, radius);
  }

  public List<Location> findByAreaAsMiles(BigDecimal latitude, BigDecimal longitude, Integer radius) {
    return locationRepository.findByAreaAsMiles(latitude, longitude, radius);
  }

  public List<Location> findByAssetsAndDestination(UUID assetId) {
    return locationRepository.findByAssetAndDestination(assetId);
  }

  public List<Location> findByAssetsAndSource(UUID assetId) {
    return locationRepository.findByAssetAndSource(assetId);
  }

  public List<Location> findByBuyerAndDestination(UUID userId) {
    return locationRepository.findByBuyerAndDestination(userId);
  }

  public List<Location> findByBuyerAndSource(UUID userId) {
    return locationRepository.findByBuyerAndSource(userId);
  }

  public List<Location> findByDestintaion(UUID locationId) {
    return locationRepository.findByDestination(locationId);
  }

  public List<Location> findByDestinationAndSeller(UUID userId) {
    return locationRepository.findByDestinationAndSeller(userId);
  }

  public List<Location> findBySellerAndSource(UUID userId) {
    return locationRepository.findBySellerAndSource(userId);
  }

  public List<Location> findBySource(UUID locationId) {
    return locationRepository.findBySource(locationId);
  }

}
