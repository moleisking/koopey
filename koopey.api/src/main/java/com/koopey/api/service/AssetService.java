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

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LocationService locationService;

    BaseRepository<Asset, UUID> getRepository() {
        return assetRepository;
    }

    @Override
    public void delete(Asset asset) {
        advertService.deleteById(asset.getAdvert().getId());
        asset.getImages().forEach((image) -> {
            imageService.deleteById(image.getId());
        });
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
