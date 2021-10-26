package com.koopey.api.repository;

import com.koopey.api.model.entity.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends BaseRepository<Location, UUID> {

  public Optional<Location> findById(@Param("id") UUID id);

  @Query(nativeQuery = true, value = "SELECT Location.* , "
      + " (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
      + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
  public List<Location> findByAreaAsKilometer(@Param("latitude") BigDecimal latitude,
      @Param("longitude") BigDecimal longitude, @Param("range") Integer radius);

  @Query(nativeQuery = true, value = "SELECT Location.* , "
      + " (3959 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
      + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
  public List<Location> findByAreaAsMiles(@Param("latitude") BigDecimal latitude,
      @Param("longitude") BigDecimal longitude, @Param("range") Integer radius);
}
