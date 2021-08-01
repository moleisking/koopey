package com.koopey.api.repository;

import com.koopey.api.model.entity.Journey;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends CrudRepository<Journey, UUID>{
    
    @Query(nativeQuery = true, value = "SELECT * FROM Journey C WHERE asset_id = :asset_id AND location_id = :location_id ")
    public int findDuplicate(@Param("asset_id")UUID assetId, @Param("location_id")UUID locationId);

}
