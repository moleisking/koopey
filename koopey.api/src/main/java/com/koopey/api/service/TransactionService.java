package com.koopey.api.service;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.TransactionRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.koopey.api.service.impl.ITransactionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService<Transaction, UUID> implements ITransactionService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TransactionRepository transactionRepository;

    TransactionService(KafkaTemplate<String, String> kafkaTemplate,
            @Lazy TransactionRepository transactionRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionRepository = transactionRepository;
    }

    protected BaseRepository<Transaction, UUID> getRepository() {
        return transactionRepository;
    }

    protected KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
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

    public Page<List<Transaction>> findBetweenDates(UUID userId, Date start, Date end, Pageable pageable) {
        return transactionRepository.findByBuyerIdOrSellerIdAndEndBetween(userId, userId, start, end, pageable);
    }

    public List<Transaction> findByBuyer(UUID userId) {
        return transactionRepository
                .findByAssetIdNotNullAndBuyerIdAndDestinationIdNotNullAndSellerIdNotNullAndSourceIdNotNull(userId);
    }

    public Page<List<Transaction>> findByBuyer(UUID userId, Pageable pagable) {
        return transactionRepository
                .findByAssetIdNotNullAndBuyerIdAndDestinationIdNotNullAndSellerIdNotNullAndSourceIdNotNull(userId,
                        pagable);
    }

    public List<Transaction> findByBuyerOrSeller(UUID userId) {
        return transactionRepository.findByBuyerIdOrSellerId(userId, userId);
    }

    public Page<List<Transaction>> findByBuyerOrSeller(UUID userId, Pageable pageable) {
        return transactionRepository.findByBuyerIdOrSellerId(userId, userId, pageable);
    }

    public List<Transaction> findByDestination(UUID locationId) {
        return transactionRepository.findByDestinationId(locationId);
    }

    public Page<List<Transaction>> findByDestination(UUID locationId, Pageable pageable) {
        return transactionRepository.findByDestinationId(locationId, pageable);
    }

    public List<Transaction> findBySeller(UUID userId) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdAndSourceIdNotNull(userId);
    }

    public Page<List<Transaction>> findBySeller(UUID userId, Pageable pageable) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdAndSourceIdNotNull(userId, pageable);
    }

    public List<Transaction> findBySource(UUID locationId) {
        return transactionRepository.findBySourceId(locationId);
    }

    public Page<List<Transaction>> findBySource(UUID locationId, Pageable pageable) {
        return transactionRepository.findBySourceId(locationId, pageable);
    }

    public List<Transaction> findByAsset(UUID assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

    public Page<List<Transaction>> findByAsset(UUID assetId, Pageable pageable) {
        return transactionRepository.findByAssetId(assetId, pageable);
    }

    public Transaction findFirstByAssetAndType(UUID assetId, String type) {
        return transactionRepository.findFirstByAssetIdAndType(assetId, type);
    }

    public List<Transaction> findByAssetAndType(UUID assetId, String type) {
        return transactionRepository.findByAssetIdAndType(assetId, type);
    }

    public Page<List<Transaction>> findByAssetAndType(UUID assetId, String type, Pageable pageable) {
        return transactionRepository.findByAssetIdAndType(assetId, type, pageable);
    }

    public List<Transaction> findByQuote(SearchDto search) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdNotNullAndSourceIdNotNullAndType("quote");
    }

    public Page<List<Transaction>> findByQuote(SearchDto search, Pageable pageable) {
        return transactionRepository.findByAssetIdNotNullAndSellerIdNotNullAndSourceIdNotNullAndType("quote", pageable);
    }

    public Boolean hasBuyerAndSeller(Transaction transaction) {
        return hasBuyer(transaction) && hasSeller(transaction);
    }

    public Boolean hasBuyer(Transaction transaction) {
        return transaction.getBuyerId() != null && !transaction.getBuyerId().toString().isEmpty();
    }

    public Boolean hasBuyerOnly(Transaction transaction) {
        return hasBuyer(transaction) && !hasSeller(transaction);
    }

    public Boolean hasSeller(Transaction transaction) {
        return transaction.getSellerId() != null && !transaction.getSellerId().toString().isEmpty();
    }

    public Boolean hasSellerOnly(Transaction transaction) {
        return !hasBuyer(transaction) && hasSeller(transaction);
    }

    public Boolean isDuplicate(Transaction transaction) {
        return transaction.getId() != null && exists(transaction.getId());
    }

}
