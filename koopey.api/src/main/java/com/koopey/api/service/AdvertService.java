package com.koopey.api.service;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.AdvertRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertService extends AuditService <Advert, UUID> {
    
    @Autowired
    AdvertRepository advertRepository;

    protected AuditRepository<Advert, UUID> getRepository() {       
        return advertRepository;
    }

}
