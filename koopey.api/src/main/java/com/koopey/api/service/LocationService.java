package com.koopey.api.service;

import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.LocationRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import com.koopey.api.service.impl.ILocationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends BaseService<Location, UUID> implements ILocationService {

  private final LocationRepository locationRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  LocationService(KafkaTemplate<String, String> kafkaTemplate,
      @Lazy LocationRepository locationRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.locationRepository = locationRepository;
  }

  protected BaseRepository<Location, UUID> getRepository() {
    return locationRepository;
  }

  protected KafkaTemplate<String, String> getKafkaTemplate() {
    return kafkaTemplate;
  }

  public long count() {
    return locationRepository.count();
  }

  public List<Location> findByAreaAsKilometer(BigDecimal latitude, BigDecimal longitude, Integer radius) {
    return locationRepository.findByAreaAsKilometer(latitude, longitude, radius);
  }

  public Page<List<Location>> findByAreaAsKilometer(BigDecimal latitude, BigDecimal longitude, Integer radius,
      Pageable pagable) {
    return locationRepository.findByAreaAsKilometer(latitude, longitude, radius, pagable);
  }

  public List<Location> findByAreaAsMiles(BigDecimal latitude, BigDecimal longitude, Integer radius) {
    return locationRepository.findByAreaAsMiles(latitude, longitude, radius);
  }

  public Page<List<Location>> findByAreaAsMiles(BigDecimal latitude, BigDecimal longitude, Integer radius,
      Pageable pageable) {
    return locationRepository.findByAreaAsMiles(latitude, longitude, radius, pageable);
  }

  public List<Location> findByAssetsAndDestination(UUID assetId) {
    return locationRepository.findByAssetAndDestination(assetId);
  }

  public Page<List<Location>> findByAssetsAndDestination(UUID assetId, Pageable pageable) {
    return locationRepository.findByAssetAndDestination(assetId, pageable);
  }

  public List<Location> findByAssetsAndSource(UUID assetId) {
    return locationRepository.findByAssetAndSource(assetId);
  }

  public Page<List<Location>> findByAssetsAndSource(UUID assetId, Pageable pageable) {
    return locationRepository.findByAssetAndSource(assetId, pageable);
  }

  public List<Location> findByBuyerAndDestination(UUID userId) {
    return locationRepository.findByBuyerAndDestination(userId);
  }

  public Page<List<Location>> findByBuyerAndDestination(UUID userId, Pageable pageable) {
    return locationRepository.findByBuyerAndDestination(userId, pageable);
  }

  public List<Location> findByBuyerAndSource(UUID userId) {
    return locationRepository.findByBuyerAndSource(userId);
  }

  public Page<List<Location>> findByBuyerAndSource(UUID userId, Pageable pageable) {
    return locationRepository.findByBuyerAndSource(userId, pageable);
  }

  public List<Location> findByDestintaion(UUID locationId) {
    return locationRepository.findByDestination(locationId);
  }

  public Page<List<Location>> findByDestintaion(UUID locationId, Pageable pageable) {
    return locationRepository.findByDestination(locationId, pageable);
  }

  public List<Location> findByDestinationAndSeller(UUID userId) {
    return locationRepository.findByDestinationAndSeller(userId);
  }

  public Page<List<Location>> findByDestinationAndSeller(UUID userId, Pageable pageable) {
    return locationRepository.findByDestinationAndSeller(userId, pageable);
  }

  public List<Location> findByOwner(UUID ownerId) {
    return locationRepository.findByOwnerId(ownerId);
  }

  public List<Location> findBySellerAndSource(UUID userId) {
    return locationRepository.findBySellerAndSource(userId);
  }

  public Page<List<Location>> findBySellerAndSource(UUID userId, Pageable pageable) {
    return locationRepository.findBySellerAndSource(userId, pageable);
  }

  public List<Location> findBySource(UUID locationId) {
    return locationRepository.findBySource(locationId);
  }

  public Page<List<Location>> findBySource(UUID locationId, Pageable pageable) {
    return locationRepository.findBySource(locationId, pageable);
  }

  public Boolean isDuplicate(Location location) {
    if (location.getId() != null && exists(location.getId())) {
      return true;
    } else {
      return false;
    }
  }

}
