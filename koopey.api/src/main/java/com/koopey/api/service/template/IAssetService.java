package com.koopey.api.service.template;

import com.koopey.api.model.entity.Asset;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAssetService {

    List<Asset> findByBuyer(UUID userId);

    Page<List<Asset>> findByBuyer(UUID userId, Pageable pagable);

    List<Asset> findByBuyerOrSeller(UUID userId);

    Page<List<Asset>> findByBuyerOrSeller(UUID userId, Pageable pagable);

    List<Asset> findByDestination(UUID locationId);

    Page<List<Asset>> findByDestination(UUID locationId, Pageable pagable);

    List<Asset> findBySeller(UUID userId);

    Page<List<Asset>> findBySeller(UUID userId, Pageable pagable);

    List<Asset> findBySource(UUID locationId);

    Page<List<Asset>> findBySource(UUID locationId, Pageable pagable);

    Boolean isDuplicate(Asset asset);

    Boolean updateAvailable(UUID assetId, Boolean available);
}
