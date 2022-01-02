package com.koopey.api.repository;

import com.koopey.api.model.entity.Advert;
import com.koopey.api.repository.base.AuditRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends AuditRepository<Advert, UUID> {}