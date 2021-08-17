package com.koopey.api.service;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.AdvertRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertService extends BaseService <Advert, UUID> {
    
    @Autowired
    AdvertRepository advertRepository;

    BaseRepository<Advert, UUID> getRepository() {       
        return advertRepository;
    }

}
