package com.koopey.api.repository;

import com.koopey.api.model.entity.Transaction;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID>{

    public List<Transaction> findByReceiverIdOrSenderId(@Param("receiver_id") UUID receiverId, @Param("sender_id") UUID senderId);
}