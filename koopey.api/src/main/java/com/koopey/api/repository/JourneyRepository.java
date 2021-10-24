package com.koopey.api.repository;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Journey;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JourneyRepository extends BaseRepository<Journey, UUID> {

        public int countByDriverIdAndLocationIdAndPassangerIdAndVehicleId(UUID driverId, UUID locationId,
                        UUID passangerId, UUID vehicleId);

        @Transactional
        public void deleteByDriverId(UUID userId);

        @Transactional
        public void deleteByLocationId(UUID locationId);

        @Transactional
        public void deleteByVehicleId(UUID assetId);

        @Transactional
        public void deleteByPassangerId(UUID userId);

        public List<Journey> findByDriverId(UUID userId);

        public List<Journey> findByLocationId(UUID locationId);

        public List<Journey> findByPassangerId(UUID userId);

        public List<Journey> findByVehicleId(UUID assetId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Journey J " + "INNER JOIN User U ON  U.id = J.driver_id "
                        + "WHERE U.id = :driver_id")
        public List<User> findDrivers(@Param("driver_id") UUID driverId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Journey J"
                        + "INNER JOIN Location L ON L.id = J.location_id " + "WHERE location_id = :location_id")
        public List<Location> findLocations(@Param("location_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Journey J "
                        + "INNER JOIN User U ON U.id = J.passanger_id " + "WHERE U.id = :passanger_id")
        public List<User> findPassangers(@Param("passanger_id") UUID passangerId);

        @Query(nativeQuery = true, value = "SELECT Asset.* FROM Journey J " + "INNER JOIN Asset A ON A.id = J.asset_id "
                        + "WHERE asset_id = :asset_id")
        public List<Asset> findVehicles(@Param("asset_id") UUID assetId);

}
