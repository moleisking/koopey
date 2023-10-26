package com.koopey.api.repository;

import com.koopey.api.model.entity.Wallet;
import com.koopey.api.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository  extends BaseRepository<Wallet, UUID>{
    
    public List<Wallet> findByOwnerId(@Param("id") UUID id);
}
