package com.koopey.api.service;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.koopey.api.model.entity.Wallet;
import com.koopey.api.repository.WalletRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WalletService extends BaseService<Wallet, UUID> {

    private final WalletRepository walletRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    WalletService(KafkaTemplate<String, String> kafkaTemplate,
            @Lazy WalletRepository walletRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.walletRepository = walletRepository;
    }

    protected BaseRepository<Wallet, UUID> getRepository() {
        return walletRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }
}
