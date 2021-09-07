package com.koopey.api.service;

import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.TransactionRepository;
import com.koopey.api.repository.BaseRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService <Transaction, UUID> {
    
    @Autowired
    TransactionRepository transactionRepository;

    BaseRepository<Transaction, UUID> getRepository() {       
        return transactionRepository;
    }

    public List<Transaction> findMyTransactions(UUID userId) {
		return transactionRepository.findByReceiverOrSender(userId);
	}
}
