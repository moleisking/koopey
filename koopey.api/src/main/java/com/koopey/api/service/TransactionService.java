package com.koopey.api.service;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.TransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService<Transaction, UUID> {

    @Autowired
    TransactionRepository transactionRepository;

    BaseRepository<Transaction, UUID> getRepository() {
        return transactionRepository;
    }

    public Long countByAsset(Transaction transaction) {
        return transactionRepository.countByIdAndAssetId(transaction.getId(), transaction.getAssetId());
    }

    public Long countByBuyer(Transaction transaction) {
        return transactionRepository.countByIdAndBuyerId(transaction.getId(), transaction.getBuyerId());
    }

    public Long countBySeller(Transaction transaction) {
        return transactionRepository.countByIdAndSellerId(transaction.getId(), transaction.getSellerId());
    }

    public List<Transaction> findBetweenDates(UUID userId, Date start, Date end) {
        return transactionRepository.findByBuyerIdOrSellerIdAndEndBetween(userId, userId, start, end);
    }

    public Page<List<Transaction>> findBetweenDates(UUID userId, Date start, Date end, Pageable pagable) {
        return transactionRepository.findByBuyerIdOrSellerIdAndEndBetween(userId, userId, start, end, pagable);
    }

    public List<Transaction> findByBuyer(UUID userId) {
        return transactionRepository.findByBuyerId(userId);
    }

    public Page<List<Transaction>> findByBuyer(UUID userId, Pageable pagable) {
        return transactionRepository.findByBuyerId(userId, pagable);
    }

    public List<Transaction> findByBuyerOrSeller(UUID userId) {
        return transactionRepository.findByBuyerIdOrSellerId(userId, userId);
    }

    public Page<List<Transaction>> findByBuyerOrSeller(UUID userId, Pageable pagable) {
        return transactionRepository.findByBuyerIdOrSellerId(userId, userId, pagable);
    }

    public List<Transaction> findByDestination(UUID locationId) {
        return transactionRepository.findByDestinationId(locationId);
    }

    public Page<List<Transaction>> findByDestination(UUID locationId, Pageable pagable) {
        return transactionRepository.findByDestinationId(locationId, pagable);
    }

    public List<Transaction> findBySeller(UUID userId) {
        return transactionRepository.findBySellerId(userId);
    }

    public Page<List<Transaction>> findBySeller(UUID userId, Pageable pagable) {
        return transactionRepository.findBySellerId(userId, pagable);
    }

    public List<Transaction> findBySource(UUID locationId) {
        return transactionRepository.findBySourceId(locationId);
    }

    public Page<List<Transaction>> findBySource(UUID locationId, Pageable pagable) {
        return transactionRepository.findBySourceId(locationId, pagable);
    }

    public List<Transaction> findByAsset(UUID assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

    public Page<List<Transaction>> findByAsset(UUID assetId, Pageable pagable) {
        return transactionRepository.findByAssetId(assetId, pagable);
    }

    public List<Transaction> findByQuote(SearchDto search) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdNotNullAndSourceIdNotNullAndType("quote");
    }

    public Page<List<Transaction>> findByQuote(SearchDto search, Pageable pagable) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdNotNullAndSourceIdNotNullAndType("quote", pagable);
    }

    public Boolean hasBuyerAndSeller(Transaction transaction) {
        if (hasBuyer(transaction) && hasSeller(transaction)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasBuyer(Transaction transaction) {
        if (transaction.getBuyerId() != null && transaction.getBuyerId().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasBuyerOnly(Transaction transaction) {
        if (hasBuyer(transaction) && !hasSeller(transaction)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasSeller(Transaction transaction) {
        if (transaction.getSellerId() != null && transaction.getSellerId().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasSellerOnly(Transaction transaction) {
        if (!hasBuyer(transaction) && hasSeller(transaction)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isDuplicate(Transaction transaction) {
        if (transaction.getId() != null && exists(transaction.getId())) {
            return true;
        } else {
            return false;
        }
    }

}
