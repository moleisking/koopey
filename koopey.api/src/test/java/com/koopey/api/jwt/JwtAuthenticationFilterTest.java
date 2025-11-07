package com.koopey.api.jwt;

import com.koopey.api.ServerApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServerApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class JwtAuthenticationFilterTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter ;

    @Test
    @WithUserDetails(value = "test")
    public void whenNoAuthenticationWhiteList_thenTrue() throws Exception {

        var request =  new MockHttpServletRequest() {
            @Override
            public void setRequestURI(String requestURI) {
                super.setRequestURI("/authentication/login");
            }
        };

        boolean exist = jwtAuthenticationFilter.shouldNotFilter(request);

        assertTrue(exist);
    }

    @Test
    @WithUserDetails(value = "test")
    public void whenAuthenticationWhiteList_thenTrue() throws Exception {

        var request =  new MockHttpServletRequest() {
            @Override
            public void setRequestURI(String requestURI) {
                super.setRequestURI("/actuator/health");
            }
        };

        boolean exist = jwtAuthenticationFilter.shouldNotFilter(request);

        assertTrue(exist);
    }

    @Test
    @WithUserDetails(value = "test")
    public void whenAuthenticationWhiteList_thenFalse() throws Exception {

        var request =  new MockHttpServletRequest() {
            @Override
            public void setRequestURI(String requestURI) {
                super.setRequestURI("/abc");
            }
        };

        boolean exist = jwtAuthenticationFilter.shouldNotFilter(request);

        assertFalse(exist);
    }
}
