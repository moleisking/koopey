package com.koopey.api.service;

import com.koopey.api.model.entity.Image;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.ImageRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService <Image, UUID> {
    
    @Autowired
    ImageRepository imageRepository;

    BaseRepository<Image, UUID> getRepository() {       
        return imageRepository;
    }
}
