package com.koopey.api.service;

import com.koopey.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CompetitionService {
    
    @Autowired
    CompetitionRepository competitionRepository;

}
