package com.koopey.api.configuration;

import com.koopey.api.configuration.jwt.*;

import java.util.Arrays;
import java.util.Collection;

import com.koopey.api.service.JwtService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.lang.Converter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    WebSecurityConfiguration(@Lazy JwtService jwtService,
            @Lazy JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, @Lazy UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

   /* @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Lazy
    public void globalUserDetails(@Lazy AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

   @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

  /*  @Bean
    public JwtExceptionFilter jwtExceptionFilterBean() throws Exception {
        return new JwtExceptionFilter(/wtAuthenticationEntryPoint,jwtTokenUtility);
    }*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers("/actuator", "/actuator/**", "/configuration/ui",
                "/configuration/security", "/swagger/**", "/swagger-resources", "/swagger-resources/**",
                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/webjars/**", "/error");
                //"/v2/api-docs",
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);


              //  cors().and().csrf().disable();
        // http.ignoringAntMatchers("/actuator/**")

        // .antMatchers("/", "/api/**", "/base/**", "/error", "/favicon.ico",
        // "/health", "/graphql/**")
        // .permitAll()

        // No authentication needed
        httpSecurity
                .authorizeRequests(auth -> auth
                        .requestMatchers("/token/**").permitAll()
                        .anyRequest().authenticated());

        httpSecurity
                .authorizeRequests(auth -> auth
                        .requestMatchers("/actuator/**", "/authenticate/**", "/configuration/ui", "/configuration/security",
                                "/swagger/**", "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**",
                                "/swagger-ui.html/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**","/error").hasRole("ADMINISTRATOR")
                        .anyRequest().authenticated());

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

       /* httpSecurity
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());*/

               /* httpSecurity.requestMatchers( "/actuator/**", "/authenticate/**", "/configuration/ui", "/configuration/security",
                        "/swagger/**", "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**",
                        "/swagger-ui.html/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**","/error")
                .permitAll().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/




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


        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
      //  http.addFilterBefore(exceptionTranslationFilter(), JwtAuthenticationTokenFilter.class);
        httpSecurity
                .logout( logout -> logout.logoutUrl("/signout").permitAll());

        return httpSecurity.build();
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(
                Arrays.asList("http://192.168.1.81:4200","http://192.168.1.180:4200", "http://127.0.0.1:4200", "http://localhost:4200",
                        "https://*.koopey.com"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}