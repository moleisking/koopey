package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
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

    public List<Transaction> findCustomer(UUID userId) {
        return transactionRepository.findByCustomerId(userId);
    }
    
    public List<User> findCustomers(UUID customerId) {
        return transactionRepository.findCustomers(customerId);
    }

    public List<Transaction> findDestination(UUID locationId) {
        return transactionRepository.findByDestinationId(locationId);
    }

    public List<Location> findDestinations(UUID assetId) {
        return transactionRepository.findDestinations(assetId);
    }

    public List<User> findProviders(UUID providerId) {
        return transactionRepository.findProviders(providerId);
    }

    public List<Transaction> findProvider(UUID userId) {
        return transactionRepository.findByProviderId(userId);
    }

    public List<Transaction> findSource(UUID locationId) {
        return transactionRepository.findBySourceId(locationId);
    }

    public List<Location> findSources(UUID assetId) {
        return transactionRepository.findSources(assetId);
    }

    public List<Transaction> findAsset(UUID assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

    public List<Asset> findAssets(UUID assetId) {
        return transactionRepository.findAssets(assetId);
    }

}
