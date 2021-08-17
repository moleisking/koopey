package com.koopey.api.service;

import com.koopey.api.model.entity.Review;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.ReviewRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService  extends BaseService <Review, UUID> {

    @Autowired
    ReviewRepository reviewRepository;

    BaseRepository<Review, UUID> getRepository() {       
        return reviewRepository;
    }

    
}
