package com.koopey.api.service;

import com.koopey.api.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JourneyService {
    
    @Autowired
    JourneyRepository journeyRepository;
}
