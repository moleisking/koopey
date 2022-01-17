package com.koopey.api.configuration;

import com.koopey.api.configuration.jwt.JwtAuthenticationEntryPoint;
import com.koopey.api.configuration.jwt.JwtAuthenticationFilter;
import java.util.Arrays;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/configuration/ui", "/configuration/security", "/graphql/**", "/swagger/**",
                "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs",
                "/v3/api-docs/**", "/webjars/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        // http.ignoringAntMatchers("/actuator/**")

        // Unauthorized access permitted
        http.authorizeRequests()
                .antMatchers("/", "/actuator/**", "/api/**", "/authenticate/**", "/base/**", "/error", "/favicon.ico",
                        "/heartbeat", "/graphql/**")
                .permitAll()
                .antMatchers("/configuration/ui", "/configuration/security", "/swagger/**", "/swagger-resources",
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
    }

    /*
     * @Bean public PasswordEncoder passwordEncoder() { // default strength = 10
     * return new BCryptPasswordEncoder(); }
     * 
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception { // For basic security // Spring Security 5 requires specifying
     * the password storage format
     * auth.inMemoryAuthentication().withUser("test").password("{noop}12345").roles(
     * "USER,ADMIN");
     * 
     * auth.inMemoryAuthentication().withUser("bcrypt") .password(
     * "{bcrypt}$2y$12$z62bivevxMRd6h.ceuFsaukusUE8B8zYmzdNlUmmkdn./lXm/Nl/u").roles
     * ("USER");
     * 
     * auth.inMemoryAuthentication().withUser("sha256") .password(
     * "{sha256}5994471ABB01112AFCC18159F6CC74B4F511B99806DA59B3CAF5A9C173CACFC5").
     * roles("ADMIN");
     * 
     * // https://developer.okta.com/blog/2019/05/15/spring-boot-login-options
     * 
     * }
     */

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(
                Arrays.asList("http://127.0.0.1:4200", "http://localhost:4200", "https://*.koopey.com"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}