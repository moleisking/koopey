package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.repository.AssetRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
        asset.getLocations().forEach((location) -> {
            locationService.deleteById(location.getId());
        });

        asset.getReviews().forEach((review) -> {
            reviewService.deleteById(review.getId());
        });
        assetRepository.delete(asset);
    }

}
