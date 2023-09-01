package com.koopey.api.service;

import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.LocationRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
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
public class LocationService extends AuditService<Location, UUID> implements ILocationService {

  private final LocationRepository locationRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  LocationService(KafkaTemplate<String, String> kafkaTemplate,
      @Lazy LocationRepository locationRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.locationRepository = locationRepository;
  }

  protected AuditRepository<Location, UUID> getRepository() {
    return locationRepository;
  }

  protected KafkaTemplate<String, String> getKafkaTemplate() {
    return kafkaTemplate;
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
      Pageable pagable) {
    return locationRepository.findByAreaAsMiles(latitude, longitude, radius, pagable);
  }

  public List<Location> findByAssetsAndDestination(UUID assetId) {
    return locationRepository.findByAssetAndDestination(assetId);
  }

  public Page<List<Location>> findByAssetsAndDestination(UUID assetId, Pageable pagable) {
    return locationRepository.findByAssetAndDestination(assetId, pagable);
  }

  public List<Location> findByAssetsAndSource(UUID assetId) {
    return locationRepository.findByAssetAndSource(assetId);
  }

  public Page<List<Location>> findByAssetsAndSource(UUID assetId, Pageable pagable) {
    return locationRepository.findByAssetAndSource(assetId, pagable);
  }

  public List<Location> findByBuyerAndDestination(UUID userId) {
    return locationRepository.findByBuyerAndDestination(userId);
  }

  public Page<List<Location>> findByBuyerAndDestination(UUID userId, Pageable pagable) {
    return locationRepository.findByBuyerAndDestination(userId, pagable);
  }

  public List<Location> findByBuyerAndSource(UUID userId) {
    return locationRepository.findByBuyerAndSource(userId);
  }

  public Page<List<Location>> findByBuyerAndSource(UUID userId, Pageable pagable) {
    return locationRepository.findByBuyerAndSource(userId, pagable);
  }

  public List<Location> findByDestintaion(UUID locationId) {
    return locationRepository.findByDestination(locationId);
  }

  public Page<List<Location>> findByDestintaion(UUID locationId, Pageable pagable) {
    return locationRepository.findByDestination(locationId, pagable);
  }

  public List<Location> findByDestinationAndSeller(UUID userId) {
    return locationRepository.findByDestinationAndSeller(userId);
  }

  public Page<List<Location>> findByDestinationAndSeller(UUID userId, Pageable pagable) {
    return locationRepository.findByDestinationAndSeller(userId, pagable);
  }

  public List<Location> findBySellerAndSource(UUID userId) {
    return locationRepository.findBySellerAndSource(userId);
  }

  public Page<List<Location>> findBySellerAndSource(UUID userId, Pageable pagable) {
    return locationRepository.findBySellerAndSource(userId, pagable);
  }

  public List<Location> findBySource(UUID locationId) {
    return locationRepository.findBySource(locationId);
  }

  public Page<List<Location>> findBySource(UUID locationId, Pageable pagable) {
    return locationRepository.findBySource(locationId, pagable);
  }

  public Boolean isDuplicate(Location location) {
    if (location.getId() != null && exists(location.getId())) {
      return true;
    } else {
      return false;
    }
  }

}
