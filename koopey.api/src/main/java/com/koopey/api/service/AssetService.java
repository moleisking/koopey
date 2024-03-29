package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.repository.AssetRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import com.koopey.api.service.impl.IAssetService;
import com.koopey.api.service.ClassificationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssetService extends BaseService<Asset, UUID> implements IAssetService{

    private final AdvertService advertService;
    private final AssetRepository assetRepository;
    private final ClassificationService classificationService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LocationService locationService;

    AssetService(@Lazy AdvertService advertService, @Lazy AssetRepository assetRepository, @Lazy ClassificationService classificationService,
            @Lazy LocationService locationService, KafkaTemplate<String, String> kafkaTemplate) {
        this.advertService = advertService;
        this.assetRepository = assetRepository;
                this.classificationService = classificationService;
        this.kafkaTemplate = kafkaTemplate;
        this.locationService = locationService;
    }

    protected BaseRepository<Asset, UUID> getRepository() {
        return assetRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @Override
    public void delete(Asset asset) {
        advertService.deleteById(asset.getAdvert().getId());
        classificationService.deleteById(asset.getId());        
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

    public List<Asset> findDefault(UUID userId) {
        return assetRepository.findDefault(userId, (System.currentTimeMillis() / 1000) - 365*(86400) );        
    }

    public List<Asset> findByBuyer(UUID userId) {
        return assetRepository.findByBuyer(userId);
    }

    public Page<List<Asset>> findByBuyer(UUID userId, Pageable pageable) {
        return assetRepository.findByBuyer(userId, pageable);
    }

    public List<Asset> findByBuyerOrSeller(UUID userId) {
        return assetRepository.findByBuyerOrSeller(userId);
    }

    public Page<List<Asset>> findByBuyerOrSeller(UUID userId, Pageable pageable) {
        return assetRepository.findByBuyerOrSeller(userId, pageable);
    }

    public List<Asset> findByDestination(UUID locationId) {
        return assetRepository.findByDestination(locationId);
    }

    public Page<List<Asset>> findByDestination(UUID locationId, Pageable pageable) {
        return assetRepository.findByDestination(locationId, pageable);
    }

    public List<Asset> findBySeller(UUID userId) {
        return assetRepository.findBySeller(userId);
    }

    public Page<List<Asset>> findBySeller(UUID userId, Pageable pageable) {
        return assetRepository.findBySeller(userId, pageable);
    }

    public List<Asset> findBySource(UUID locationId) {
        return assetRepository.findBySource(locationId);
    }

    public Page<List<Asset>> findBySource(UUID locationId, Pageable pageable) {
        return assetRepository.findBySource(locationId, pageable);
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
