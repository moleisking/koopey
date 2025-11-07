package com.koopey.api.configuration;

import java.util.Arrays;

import com.koopey.api.jwt.CustomAccessDeniedHandler;
import com.koopey.api.jwt.CustomAuthenticationFailureHandler;
import com.koopey.api.jwt.JwtAuthenticationEntryPoint;
import com.koopey.api.jwt.JwtAuthenticationFilter;
import com.koopey.api.service.JwtService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(/*debug = true*/)
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

  /*  @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }*/

  /*  @Bean
    public JwtExceptionFilter jwtExceptionFilterBean() throws Exception {
        return new JwtExceptionFilter(/wtAuthenticationEntryPoint,jwtTokenUtility);
    }*/

   @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(ArrayUtils.addAll(AUTHENTICATION_WHITE_LIST, NO_AUTHENTICATION_WHITE_LIST) );
        //"/v2/api-docs",
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsFilter()))
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(getRequestMatcherList(AUTHENTICATION_WHITE_LIST )).authenticated()
                                    .requestMatchers(getRequestMatcherList(NO_AUTHENTICATION_WHITE_LIST )).permitAll();
                        }
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager(httpSecurity.getSharedObject(HttpSecurity.class)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)).build();


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


      //  return httpSecurity.build();
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
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Lazy
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
                Arrays.asList("http://192.168.1.*:4200", "http://127.0.0.1:4200",
                        "http://localhost:4200", "https://*.koopey.com"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type", "TraceId"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

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
            "/license/**",
            "/graphql/**",
            "/swagger/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/token/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    public static RequestMatcher[] getRequestMatcherList( String[] list) {
        var antPathRequestMatcher = new RequestMatcher[list.length];
        for (int i = 0; i < list.length; i++) {
            antPathRequestMatcher[i] = PathPatternRequestMatcher.withDefaults().matcher(list[i]);
        }
        return antPathRequestMatcher;
    }
}