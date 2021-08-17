package com.koopey.api.service;

import com.koopey.api.repository.ClassificationRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ClassificationService {
    
    @Autowired
    ClassificationRepository classificationRepository;
}
