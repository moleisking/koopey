package com.koopey.api.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class ServerConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void whenConnectingToDatabase_thenConnectionShouldBeValid() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
        }
    }
}
