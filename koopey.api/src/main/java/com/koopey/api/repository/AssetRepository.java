package com.koopey.api.repository;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Search;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BaseRepository<Asset, UUID>{

    Page<Asset> findByName(Search search, Pageable pagable);

}