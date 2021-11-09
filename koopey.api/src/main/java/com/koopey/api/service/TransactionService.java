package com.koopey.api.service;

import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.TransactionRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService<Transaction, UUID> {

    @Autowired
    TransactionRepository transactionRepository;

    BaseRepository<Transaction, UUID> getRepository() {
        return transactionRepository;
    }

    public Long countByBuyer(Transaction transaction) {
        return transactionRepository.countByIdAndBuyerId(transaction.getId(), transaction.getBuyerId());
    }

    public Long countBySeller(Transaction transaction) {
        return transactionRepository.countByIdAndSellerId(transaction.getId(), transaction.getSellerId());
    }

    public List<Transaction> findByBuyer(UUID userId) {
        return transactionRepository.findByBuyerId(userId);
    }
    
    public List<Transaction> findByDestination(UUID locationId) {
        return transactionRepository.findByDestinationId(locationId);
    }

    public List<Transaction> findBySeller(UUID userId) {
        return transactionRepository.findBySellerId(userId);
    }

    public List<Transaction> findBySource(UUID locationId) {
        return transactionRepository.findBySourceId(locationId);
    }

    public List<Transaction> findByAsset(UUID assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

}
