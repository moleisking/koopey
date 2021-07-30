package com.koopey.api.repository;

import com.koopey.api.model.entity.Advert;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, UUID> {}