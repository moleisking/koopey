package com.koopey.api.service;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.AdvertRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdvertService extends AuditService<Advert, UUID> {

    private final AdvertRepository advertRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    AdvertService(@Lazy AdvertRepository advertRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.advertRepository = advertRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    protected AuditRepository<Advert, UUID> getRepository() {
        return advertRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

}
