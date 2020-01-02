package com.koopey.server.configuration;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//https://www.devglan.com/spring-security/spring-boot-jwt-auth
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
         http.csrf().disable();
        // .ignoringAntMatchers("/actuator/**")

        http.authorizeRequests()
                .antMatchers("/", "/contacts", "/error", "/faq", "/heartbeat", "/privacy", "/register", "/welcome")
                .permitAll().antMatchers("/users**", "/assets").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().authenticated().and().httpBasic();

        http.authorizeRequests()//
                .antMatchers("/favicon.ico").permitAll();

        http.formLogin()//
                .defaultSuccessUrl("/admin/news")//
                .loginPage("/login")//
                .permitAll();

        http.logout().permitAll();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // http.authorizeRequests().antMatchers("/register").permitAll().antMatchers("/welcome");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Spring Security 5 requires specifying the password storage format
        auth.inMemoryAuthentication().withUser("test").password("{noop}12345").roles("USER,ADMIN");

        auth.inMemoryAuthentication().withUser("bcrypt")
                .password("{bcrypt}$2y$12$z62bivevxMRd6h.ceuFsaukusUE8B8zYmzdNlUmmkdn./lXm/Nl/u").roles("USER");

        auth.inMemoryAuthentication().withUser("sha256")
                .password("{sha256}5994471ABB01112AFCC18159F6CC74B4F511B99806DA59B3CAF5A9C173CACFC5").roles("ADMIN");

        // https://developer.okta.com/blog/2019/05/15/spring-boot-login-options

    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}