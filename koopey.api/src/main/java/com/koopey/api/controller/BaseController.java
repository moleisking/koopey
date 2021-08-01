package com.koopey.api.controller;

import com.koopey.api.model.entity.BaseEntity;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseController {
    ResponseEntity<Void> create(@RequestBody BaseEntity entity) ;
    ResponseEntity delete(@RequestBody BaseEntity entity) ;
    ResponseEntity update(@RequestBody BaseEntity entity) ;
    ResponseEntity read(UUID id) ;    
}
