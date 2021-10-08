package com.koopey.api.repository;

import com.koopey.api.model.entity.Journey;
import com.koopey.api.model.entity.Location;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends BaseRepository<Journey, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM Journey C " + "WHERE asset_id = :asset_id "
            + "AND location_id = :location_id")
    public int findDuplicate(@Param("asset_id") UUID assetId, @Param("location_id") UUID locationId);

    @Query(nativeQuery = true, value = "SELECT Location.* FROM Journey J"
            + "INNER JOIN Location L ON J.asset_id = L.asset_id " + "WHERE asset_id = :asset_id")
    public List<Location> findAssetLocations(@Param("asset_id") UUID assetId);

    @Query(nativeQuery = true, value = "SELECT Location.* FROM Journey J "
            + "INNER JOIN Location L ON J.game_id = L.asset_id " + "WHERE asset_id = :asset_id")
    public List<Location> findPassangers(@Param("asset_id") UUID assetId);

}
