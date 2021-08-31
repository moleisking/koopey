package com.koopey.api.configuration.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
   
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        String alias = null;
        String authToken = null;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                alias = jwtTokenUtility.getAliasFromToken(authToken);
            } catch (IllegalArgumentException ex) {
                log.error("an error occured during getting username from token {}", ex);
            } catch (ExpiredJwtException ex) {
                log.warn("the token is expired and not valid anymore {}",jwtTokenUtility.getExpirationDateFromToken(authToken) );
            } catch (SignatureException ex) {
                log.error("Authentication Failed. Username or Password not valid. {}", ex);
            }
        } else {
            // There is no header and therefore no token
            log.warn("couldn't find bearer string, will ignore the header");
        }
        if (alias != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.info("Authentication SecurityContextHolder.getContext()");
            UserDetails userDetails = userDetailsService.loadUserByUsername(alias);

            if (jwtTokenUtility.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("authenticated user " + alias + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}