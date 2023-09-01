package com.koopey.api.service.impl;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Tag;
import java.util.List;
import java.util.UUID;

public interface IClassificationService {

    List<Asset> findAssets(List<Tag> tags);

    List<Tag> findTags(UUID assetId);
}
