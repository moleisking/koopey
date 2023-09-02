package com.koopey.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;

import com.koopey.api.ServerApplication;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.repository.LocationRepository;
import com.koopey.api.repository.TransactionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class LocationServiceTest {
    
    
    @Mock
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @BeforeEach
    private void setup() {
        Location location = Location.builder().name("test").description("description").build();
      
        when(locationRepository.save(any(Location.class))).thenReturn(location);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testReadLocation() {
        assertThat(locationService.count()).isGreaterThan(0);
    }
}
