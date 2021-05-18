package com.koopey.api.repository;

import com.koopey.api.model.Asset;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID>{



}