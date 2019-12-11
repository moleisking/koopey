package com.koopey.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Spring Security 5 requires specifying the password storage format
        auth.inMemoryAuthentication().withUser("test").password("{noop}12345").roles("USER,ADMIN");

        auth.inMemoryAuthentication().withUser("bcrypt")
                .password("{bcrypt}$2y$12$z62bivevxMRd6h.ceuFsaukusUE8B8zYmzdNlUmmkdn./lXm/Nl/u").roles("USER");

        auth.inMemoryAuthentication().withUser("sha256")
                .password("{sha256}5994471ABB01112AFCC18159F6CC74B4F511B99806DA59B3CAF5A9C173CACFC5").roles("ADMIN");

                //https://developer.okta.com/blog/2019/05/15/spring-boot-login-options
    }

}