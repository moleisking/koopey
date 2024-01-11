package com.koopey.api.configuration;

import com.koopey.api.configuration.jwt.*;

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
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final  JwtTokenUtility jwtTokenUtility;
    private final UserDetailsService userDetailsService;

    WebSecurityConfiguration(@Lazy JwtTokenUtility jwtTokenUtility,
            @Lazy JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, @Lazy UserDetailsService userDetailsService) {
        this.jwtTokenUtility = jwtTokenUtility;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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

  /*  @Bean
    public JwtExceptionFilter jwtExceptionFilterBean() throws Exception {
        return new JwtExceptionFilter(/wtAuthenticationEntryPoint,jwtTokenUtility);
    }*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().antMatchers("/actuator", "/actuator/**", "/configuration/ui",
                "/configuration/security", "/swagger/**", "/swagger-resources", "/swagger-resources/**",
                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/webjars/**", "/error");
                //"/v2/api-docs",
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
                        "/swagger/**", "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**",
                        "/swagger-ui.html/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**","/error")
                .permitAll().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
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
      //  http.addFilterBefore(exceptionTranslationFilter(), JwtAuthenticationTokenFilter.class);
        http.logout().permitAll();

        return http.build();
    }

    /*@Bean
    public static ExceptionTranslationFilter exceptionTranslationFilter() {
        JwtExceptionFilter exceptionTranslationFilter = new JwtExceptionFilter(new JwtAuthenticationEntryPoint(), new JwtTokenUtility());
        JwtAccessDeniedHandler accessDeniedHandlerImpl = new JwtAccessDeniedHandler();
        exceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
        exceptionTranslationFilter.afterPropertiesSet();
        return exceptionTranslationFilter;
    }*/

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