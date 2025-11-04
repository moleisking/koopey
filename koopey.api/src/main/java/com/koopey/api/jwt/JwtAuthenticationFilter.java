package com.koopey.api.jwt;

import com.koopey.api.exception.JwtException;

import com.koopey.api.service.JwtService;
import com.koopey.api.model.type.AuthenticationType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(@Lazy JwtService jwtService,
                                   @Lazy UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws IOException, ServletException {

        var temp = request.getHeaderNames();
        String authenticationHeader = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authenticationHeader)) {
            log.warn("authentication header empty");
        } else if (!StringUtils.startsWith(authenticationHeader, TOKEN_PREFIX)) {
            log.warn("authentication header bearer string empty");
        }

        try {
            String authenticationToken = extractToken(request);
            String alias = !StringUtils.isEmpty(authenticationToken) ? jwtService.getAliasFromToken(authenticationToken) : null;

           /* if (!StringUtils.isEmpty(authenticationHeader) && StringUtils.startsWith(authenticationHeader, TOKEN_PREFIX)) {
                alias = jwtService.getAliasFromToken(authenticationToken);
                authenticationToken = authenticationHeader.replace(TOKEN_PREFIX, "");
            }*/

            //   handleSecurityContext(alias, authToken, request);
        /*} catch (JwtException ex) {
          //  handleInvalidJwtToken(response, ex);
            log.error("an error occurred during getting username from token {}", ex.getMessage());
         //   return;
        }*/
            if (Objects.nonNull(alias) && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("Context username:" + alias);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(alias);
                System.out.println("Context user details: " + userDetails);
                boolean isTokenValidated = jwtService.isTokenValid(authenticationToken, userDetails);
                System.out.println("Is token validated: " + isTokenValidated);
                if (isTokenValidated) {
                    System.out.println("UerDetails authorities: " + userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } else {
                throw new BadCredentialsException("Bearer token not set correctly");
            }

            //  try {
        } catch (ExpiredJwtException jwtExpiredException) {
            request.setAttribute("exception", jwtExpiredException);
            log.error("Filter jwt expired exception {}", jwtExpiredException.getMessage());
        } catch (BadCredentialsException | UnsupportedJwtException | MalformedJwtException jwtException) {
            log.error("Filter jwt bad credentials exception {}", jwtException.getMessage());
            request.setAttribute("exception", jwtException);
        } catch (Exception accessDeniedException) {
            log.error(accessDeniedException.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Jwt issue", accessDeniedException);

        }
        filterChain.doFilter(request, response);

    }

    private void handleInvalidJwtToken(HttpServletResponse response, JwtException ex) throws IOException {
        log.error("handleInvalidJwtToken {}", ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

    private void handleSecurityContext(String alias, String authToken, HttpServletRequest req) {
        if (alias != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(alias);

            if (jwtService.isTokenValid(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, List.of(
                        new SimpleGrantedAuthority(com.koopey.api.model.type.AuthenticationType.ADMINISTRATOR.toString()),
                        new SimpleGrantedAuthority(com.koopey.api.model.type.AuthenticationType.ADVERTISER.toString()),
                        new SimpleGrantedAuthority(com.koopey.api.model.type.AuthenticationType.USER.toString()),
                        new SimpleGrantedAuthority(com.koopey.api.model.type.AuthenticationType.TESTER.toString())
                ));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token)) {
            log.warn("authentication header empty");
            return null;
        } else if (!StringUtils.startsWith(token, TOKEN_PREFIX)) {
            log.warn("authentication header bearer string empty");
            return null;
        }

        return token.substring(TOKEN_PREFIX.length());
    }
/*
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }*/

}