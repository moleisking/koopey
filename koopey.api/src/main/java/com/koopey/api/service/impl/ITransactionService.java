package com.koopey.api.service.impl;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ITransactionService {

    Long countByAsset(Transaction transaction) ;

    Long countByBuyer(Transaction transaction) ;

    Long countBySeller(Transaction transaction) ;

    List<Transaction> findBetweenDates(UUID userId, Date start, Date end) ;

    Page<List<Transaction>> findBetweenDates(UUID userId, Date start, Date end, Pageable pageable);

    List<Transaction> findByBuyer(UUID userId) ;

    Page<List<Transaction>> findByBuyer(UUID userId, Pageable pagable);

    List<Transaction> findByBuyerOrSeller(UUID userId) ;

    Page<List<Transaction>> findByBuyerOrSeller(UUID userId, Pageable pageable) ;

    List<Transaction> findByDestination(UUID locationId) ;

    Page<List<Transaction>> findByDestination(UUID locationId, Pageable pageable);

    List<Transaction> findBySeller(UUID userId) ;

    Page<List<Transaction>> findBySeller(UUID userId, Pageable pageable) ;

    List<Transaction> findBySource(UUID locationId);

    Page<List<Transaction>> findBySource(UUID locationId, Pageable pageable) ;

    List<Transaction> findByAsset(UUID assetId);

    Page<List<Transaction>> findByAsset(UUID assetId, Pageable pageable) ;

    Transaction findFirstByAssetAndType(UUID assetId, String type) ;

    List<Transaction> findByAssetAndType(UUID assetId, String type) ;

    Page<List<Transaction>> findByAssetAndType(UUID assetId, String type, Pageable pageable) ;

    List<Transaction> findByQuote(SearchDto search);

    Page<List<Transaction>> findByQuote(SearchDto search, Pageable pageable) ;

    Boolean hasBuyerAndSeller(Transaction transaction) ;

    Boolean hasBuyer(Transaction transaction);

    Boolean hasBuyerOnly(Transaction transaction);

    Boolean hasSeller(Transaction transaction) ;

    Boolean hasSellerOnly(Transaction transaction);

    Boolean isDuplicate(Transaction transaction) ;

}
