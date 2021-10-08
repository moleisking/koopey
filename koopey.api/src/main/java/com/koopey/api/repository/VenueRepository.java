package com.koopey.api.repository;

import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.Venue;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends BaseRepository<Venue, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM Venue V " + "WHERE user_id = :user_id "
            + "AND location_id = :location_id")
    public int findDuplicate(@Param("user_id") UUID userId, @Param("location_id") UUID locationId);

    @Query(nativeQuery = true, value = "SELECT Location.* FROM Venue V "
            + "INNER JOIN Location L ON V.user_id = L.user_id " + "WHERE user_id = :user_id")
    public List<Location> findUserLocations(@Param("user_id") UUID userId);

}
