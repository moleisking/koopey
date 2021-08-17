package com.koopey.api.repository;

import com.koopey.api.model.entity.Transaction;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID>{
}