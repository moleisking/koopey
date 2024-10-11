package com.koopey.api.configuration;

import java.util.Arrays;

import com.koopey.api.jwt.JwtAuthenticationEntryPoint;
import com.koopey.api.jwt.JwtAuthenticationFilter;
import com.koopey.api.service.JwtService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    //  private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    SecurityConfiguration(//@Lazy JwtService jwtService,
                          @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
                          @Lazy JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          @Lazy UserDetailsService userDetailsService) {
        //  this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

 /*   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }*/

   /* @Lazy
    public void globalUserDetails(@Lazy AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/

   /* @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception */

  /*  @Bean
    public JwtExceptionFilter jwtExceptionFilterBean() throws Exception {
        return new JwtExceptionFilter(/wtAuthenticationEntryPoint,jwtTokenUtility);
    }*/

   /* @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers(WHITE_LIST);
        //"/v2/api-docs",
    }*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(getRequestMatcherWhiteList()).permitAll();

                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .logout(logout -> logout.logoutUrl("/logout").permitAll());

        // Authorized access permitted
        /*
         * http.authorizeRequests().antMatchers("/users**",
         * "/assets").access("hasRole('USER') or hasRole('ADMIN')")
         * .anyRequest().authenticated().and().exceptionHandling().
         * authenticationEntryPoint(unauthorizedHandler)
         * .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
         * STATELESS);
         */
 /*  httpSecurity
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwtService.getSignKey()));*/

        // http.formLogin().defaultSuccessUrl("/base/welcome").loginPage("/authenticate/login").permitAll();


        return httpSecurity.build();
    }

  /*  @Bean
    public static ExceptionTranslationFilter exceptionTranslationFilter() {
        JwtExceptionFilter exceptionTranslationFilter = new JwtExceptionFilter(new JwtAuthenticationEntryPoint(), new JwtService());
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
        CorsConfiguration configeration = new CorsConfiguration();
        configeration.setAllowCredentials(true);
        configeration.setAllowedOrigins(
                Arrays.asList("http://192.168.1.81:4200",
                        "http://192.168.1.180:4200",
                        "http://127.0.0.1:4200",
                        "http://localhost:4200",
                        "https://*.koopey.com"));
        configeration.addAllowedHeader("*");
        configeration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", configeration);
        return new CorsFilter(source);
    }

    private static String[] WHITE_LIST = {
            "/actuator/**",
            "/authentication/**",
            "/configuration/ui",
            "/configuration/security",
            "/error",
            "/favicon.ico",
            "/graphql/**",
            "/swagger/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/token/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    private static RequestMatcher[] getRequestMatcherWhiteList() {
        var antPathRequestMatcher = new RequestMatcher[WHITE_LIST.length];
        for (int i = 0; i < WHITE_LIST.length; i++) {
            antPathRequestMatcher[i] = new AntPathRequestMatcher(WHITE_LIST[i]);
        }
        return antPathRequestMatcher;
    }
}