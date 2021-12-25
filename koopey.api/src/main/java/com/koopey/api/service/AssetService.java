package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.repository.AssetRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssetService extends BaseService<Asset, UUID> {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    private AdvertService advertService;
  
   // @Autowired
   // private ReviewService reviewService;

    @Autowired
    private LocationService locationService;

    BaseRepository<Asset, UUID> getRepository() {
        return assetRepository;
    }

    @Override
    public void delete(Asset asset) {
        advertService.deleteById(asset.getAdvert().getId());      
        asset.getDestinations().forEach((location) -> {
            locationService.deleteById(location.getId());
        });
        asset.getSources().forEach((location) -> {
            locationService.deleteById(location.getId());
        });
        assetRepository.delete(asset);
    }

    public Boolean isDuplicate(Asset asset) {
        if (asset.getId() != null && exists(asset.getId())) {
            return true;
        } else {
            return false;
        }
    }

    public List<Asset> findByBuyer(UUID userId) {
        return assetRepository.findByBuyer(userId);
    }

    public Page<List<Asset>> findByBuyer(UUID userId, Pageable pagable) {
        return assetRepository.findByBuyer(userId , pagable);
    }

    public List<Asset> findByBuyerOrSeller(UUID userId) {
        return assetRepository.findByBuyerOrSeller(userId);
    }

    public Page<List<Asset>> findByBuyerOrSeller(UUID userId, Pageable pagable) {
        return assetRepository.findByBuyerOrSeller(userId , pagable);
    }

    public List<Asset> findByDestination(UUID locationId) {
        return assetRepository.findByDestination(locationId);
    }

    public Page<List<Asset>> findByDestination(UUID locationId, Pageable pagable) {
        return assetRepository.findByDestination(locationId, pagable);
    }

    public List<Asset> findBySeller(UUID userId) {
        return assetRepository.findBySeller(userId);
    }

    public Page<List<Asset>> findBySeller(UUID userId, Pageable pagable) {
        return assetRepository.findBySeller(userId, pagable);
    }

    public List<Asset> findBySource(UUID locationId) {
        return assetRepository.findBySource(locationId);
    }

    public Page<List<Asset>> findBySource(UUID locationId, Pageable pagable) {
        return assetRepository.findBySource(locationId, pagable);
    }

    public Boolean updateAvailable(UUID assetId, Boolean available) {
        Optional<Asset> asset = super.findById(assetId);
        if (asset.isPresent()) {
            Asset a = asset.get();
            a.setAvailable(available);
            assetRepository.save(a);
            return true;
        } else {
            return false;
        }
    }

}
