package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import com.koopey.api.repository.TransactionRepository;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Transaction;

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
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    private void setup() {
        Asset asset = Asset.builder().name("test").description("description").build();
        Transaction transaction = Transaction.builder()
                .name("test")
                .description("description")
                .asset(asset)
                .assetId(asset.getId()).build();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testReadUser() {
        assertThat(transactionService.count()).isGreaterThan(0);
    }
}
