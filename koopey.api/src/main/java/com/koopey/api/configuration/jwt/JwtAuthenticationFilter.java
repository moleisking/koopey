package com.koopey.api.configuration.jwt;

import com.koopey.api.exception.JwtException;

import com.koopey.api.service.JwtService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final JwtService jwtTokenUtility;

    public JwtAuthenticationFilter(@Lazy JwtService jwtTokenUtility,
                                   @Lazy UserDetailsService userDetailsService) {
        this.jwtTokenUtility = jwtTokenUtility;
        this.userDetailsService = userDetailsService;
    }

       @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse res, @NotNull FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        String alias = null;
        String authToken = null;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                alias = jwtTokenUtility.getAliasFromToken(authToken);
                handleSecurityContext(alias, authToken, request);
            } catch (JwtException ex) {
                handleInvalidJwtToken(res, ex);
                log.error("an error occurred during getting username from token {}", ex.getMessage());
                return;
            }
        } else {
            // There is no header and therefore no token
            log.warn("couldn't find bearer string, will ignore the header");
        }

        try {
            chain.doFilter(request, res);
        } catch (Exception accessDeniedException) {
            log.error(accessDeniedException.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Jwt issue", accessDeniedException);

        }

    }

    private void handleInvalidJwtToken(HttpServletResponse response, JwtException ex) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

    private void handleSecurityContext(String alias, String authToken, HttpServletRequest req) {
        if (alias != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(alias);

            if (jwtTokenUtility.isTokenValid(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, List.of(new SimpleGrantedAuthority("ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

}