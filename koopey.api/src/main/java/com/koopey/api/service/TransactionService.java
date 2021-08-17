package com.koopey.api.service;

import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.TransactionRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService <Transaction, UUID> {
    
    @Autowired
    TransactionRepository assetRepository;

    BaseRepository<Transaction, UUID> getRepository() {       
        return assetRepository;
    }
}
