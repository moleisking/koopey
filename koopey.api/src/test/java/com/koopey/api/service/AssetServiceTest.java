package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import com.koopey.api.repository.AssetRepository;
import com.koopey.api.model.entity.Asset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Autowired
    private TransactionService assetService;

    @BeforeEach
    public void setup() {
        Asset asset = Asset.builder().name("test").description("description").build();

        when(assetRepository.save(any(Asset.class))).thenReturn(asset);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testReadAsset() {
        assertThat(assetService.count()).isGreaterThan(0);
    }
}
