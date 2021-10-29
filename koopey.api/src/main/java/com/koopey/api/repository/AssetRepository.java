package com.koopey.api.repository;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Asset;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BaseRepository<Asset, UUID> {

    Page<Asset> findByName(SearchDto search, Pageable pagable);

}