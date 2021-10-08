package com.koopey.api.repository;

import com.koopey.api.model.entity.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends BaseRepository<Location, UUID> {
    
    public Optional<Location> findById(@Param("id") UUID id);

    public List<Location> findByOwnerId( @Param("id") UUID id);
}
