package com.koopey.api.repository;

import com.koopey.api.model.entity.Location;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    
    public Optional<Location> findById(@Param("id") UUID id);
}
