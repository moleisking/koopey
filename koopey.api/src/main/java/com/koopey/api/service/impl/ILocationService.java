package com.koopey.api.service.impl;

import com.koopey.api.model.entity.Location;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILocationService {

  List<Location> findByAreaAsKilometer(BigDecimal latitude, BigDecimal longitude, Integer radius);

  Page<List<Location>> findByAreaAsKilometer(BigDecimal latitude, BigDecimal longitude, Integer radius,
      Pageable pagable);

  List<Location> findByAreaAsMiles(BigDecimal latitude, BigDecimal longitude, Integer radius);

  Page<List<Location>> findByAreaAsMiles(BigDecimal latitude, BigDecimal longitude, Integer radius,
      Pageable pagable);

  List<Location> findByAssetsAndDestination(UUID assetId);

  Page<List<Location>> findByAssetsAndDestination(UUID assetId, Pageable pagable);

  List<Location> findByAssetsAndSource(UUID assetId);

  Page<List<Location>> findByAssetsAndSource(UUID assetId, Pageable pagable);

  List<Location> findByBuyerAndDestination(UUID userId);

  Page<List<Location>> findByBuyerAndDestination(UUID userId, Pageable pagable);

  List<Location> findByBuyerAndSource(UUID userId);

  Page<List<Location>> findByBuyerAndSource(UUID userId, Pageable pagable);

  List<Location> findByDestintaion(UUID locationId);

  Page<List<Location>> findByDestintaion(UUID locationId, Pageable pagable);

  List<Location> findByDestinationAndSeller(UUID userId);

  Page<List<Location>> findByDestinationAndSeller(UUID userId, Pageable pagable);

  List<Location> findBySellerAndSource(UUID userId);

  Page<List<Location>> findBySellerAndSource(UUID userId, Pageable pagable);

  List<Location> findBySource(UUID locationId);

  Page<List<Location>> findBySource(UUID locationId, Pageable pagable);

  Boolean isDuplicate(Location location);

}
