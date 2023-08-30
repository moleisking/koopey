package com.koopey.api.configuration;

import com.koopey.api.configuration.jwt.JwtAuthenticationEntryPoint;
import com.koopey.api.configuration.jwt.JwtAuthenticationFilter;
import com.koopey.api.configuration.jwt.JwtTokenUtility;
import java.util.Arrays;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  {

    private JwtAuthenticationEntryPoint unauthorizedHandler;
    private JwtTokenUtility jwtTokenUtility;
    private UserDetailsService userDetailsService;

    WebSecurityConfiguration(@Lazy JwtTokenUtility jwtTokenUtility,
            @Lazy JwtAuthenticationEntryPoint unauthorizedHandler, @Lazy UserDetailsService userDetailsService) {
        this.jwtTokenUtility = jwtTokenUtility;
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Lazy
    public void globalUserDetails(@Lazy AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter(jwtTokenUtility, userDetailsService);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().antMatchers("/actuator", "/actuator/**", "/configuration/ui",
                "/configuration/security",
                "/swagger/**",
                "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs",
                "/v3/api-docs/**", "/webjars/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        // http.ignoringAntMatchers("/actuator/**")

        // .antMatchers("/", "/api/**", "/base/**", "/error", "/favicon.ico",
        // "/health", "/graphql/**")
        // .permitAll()

        // No authentication needed
        http.authorizeRequests()
                .antMatchers("/actuator/**", "/authenticate/**", "/configuration/ui", "/configuration/security",
                        "/swagger/**", "/swagger-resources",
                        "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html/**", "/v2/api-docs",
                        "/v3/api-docs", "/webjars/**")
                .permitAll().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Authorized access permitted
        /*
         * http.authorizeRequests().antMatchers("/users**",
         * "/assets").access("hasRole('USER') or hasRole('ADMIN')")
         * .anyRequest().authenticated().and().exceptionHandling().
         * authenticationEntryPoint(unauthorizedHandler)
         * .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
         * STATELESS);
         */

        // http.formLogin().defaultSuccessUrl("/base/welcome").loginPage("/authenticate/login").permitAll();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.logout().permitAll();

        return http.build();
    } 

    @Bean
    @Lazy
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(
                Arrays.asList("http://192.168.1.180:4200", "http://127.0.0.1:4200", "http://localhost:4200",
                        "https://*.koopey.com"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}