package com.koopey.api.repository;

import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.base.AuditRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends AuditRepository<Game, UUID> {}
