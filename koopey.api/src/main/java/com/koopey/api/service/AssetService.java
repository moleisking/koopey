package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.repository.AssetRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService extends BaseService <Asset, UUID>{
    
    @Autowired
    AssetRepository assetRepository;

    BaseRepository<Asset, UUID> getRepository() {       
        return assetRepository;
    }

}
