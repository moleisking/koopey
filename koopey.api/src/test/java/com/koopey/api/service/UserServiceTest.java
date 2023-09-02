package com.koopey.api.service;

import com.koopey.api.ServerApplication;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.model.entity.User;

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
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Autowired
    private TagService tagService;

    @BeforeEach
    private void setup(){
       User user = User.builder().name("test").email("test@koopey.com").build();
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    @WithUserDetails(value = "test")
    public void testReadUser() {
        assertThat(tagService.count()).isGreaterThan(0);
    }
}
