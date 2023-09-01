package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    @WithUserDetails(value = "test")
    public void testReadTags() {
        assertThat(tagService.count()).isGreaterThan(0);
    }
}
