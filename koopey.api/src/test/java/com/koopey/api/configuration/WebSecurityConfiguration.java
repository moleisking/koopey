package com.koopey.api.configuration;

import com.koopey.api.ServerApplication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ContextConfiguration(classes = ServerApplication.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@TestPropertySource("classpath:application-test.properties")
public class WebSecurityConfiguration {

    public static final String[] AUTHENTICATION_WHITE_LIST = {
            "/advert/**",
            "/asset/**",
            "/authentication/update/**",
            "/authentication/read/**",
            "/classification/**",
            "/game/**",
            "/heartbeat/**",
            "/home/contact",
            "/location/**",
            "/message/**",
            "/tag/**",
            "/transaction/**",
            "/user/**",
            "/wallet/**"
    };

    public static final String[] NO_AUTHENTICATION_WHITE_LIST = {
            "/actuator/**",
            "/authentication/login",
            "/authentication/register",
            "/configuration/ui",
            "/configuration/security",
            "/error",
            "/favicon.ico",
            "/home/contact",
            "/license/**"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("test")
                .password(passwordEncoder.encode("I love to test!"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("test")
                .password(passwordEncoder().encode("I love to test!"))
                .authorities("USER");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorize -> {
                    authorize.requestMatchers(getRequestMatcherList(AUTHENTICATION_WHITE_LIST)).authenticated()
                            .requestMatchers(getRequestMatcherList(NO_AUTHENTICATION_WHITE_LIST)).permitAll();
                }).httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static RequestMatcher[] getRequestMatcherList(String[] list) {
        var antPathRequestMatcher = new RequestMatcher[list.length];
        for (int i = 0; i < list.length; i++) {
            antPathRequestMatcher[i] = PathPatternRequestMatcher.withDefaults().matcher(list[i]);
        }
        return antPathRequestMatcher;
    }
}
