package com.koopey.api.repository;

import com.koopey.api.model.entity.Review;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends BaseRepository<Review, UUID>{



}