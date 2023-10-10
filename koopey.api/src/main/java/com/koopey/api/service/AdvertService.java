package com.koopey.api.service;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.AdvertRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdvertService extends BaseService<Advert, UUID> {

    private final AdvertRepository advertRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    AdvertService(@Lazy AdvertRepository advertRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.advertRepository = advertRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    protected BaseRepository<Advert, UUID> getRepository() {
        return advertRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

}
