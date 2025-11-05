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

        if (checkForTokenCorruption(request)) {

            String authenticationToken = jwtService.extractTokenFromBearer(request.getHeader("Authorization"));

            try {
                String alias = jwtService.getAliasFromToken(authenticationToken);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(alias);

                if (jwtService.isTokenValid(authenticationToken, userDetails)) {
                    log.info("Token for {} is valid with {}.",alias,  userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    log.info("Token for {} is not valid." , alias);
                    throw new BadCredentialsException("Bearer token not set correctly");
                }

            } catch (JwtException jwtException) {
                log.error("No alias found in jwt token. {}", jwtException.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            } catch (ExpiredJwtException jwtExpiredException) {
                request.setAttribute("exception", jwtExpiredException);
                log.error("Jwt token expired. {}", jwtExpiredException.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            } catch (BadCredentialsException | UnsupportedJwtException | MalformedJwtException jwtException) {
                log.error("Jwt bad credentials. {}", jwtException.getMessage());
                request.setAttribute("exception", jwtException);
            } catch (Exception accessDeniedException) {
                log.error(accessDeniedException.getMessage());
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Jwt denied", accessDeniedException);
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
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

    private boolean checkForTokenCorruption(HttpServletRequest request) {

        if (StringUtils.isEmpty(request.getHeader("Authorization"))) {
            log.warn("authentication header empty");
            return false;
        }

        String token = jwtService.extractTokenFromBearer(request.getHeader("Authorization"));

        if (jwtService.isTokenExpired(token)) {
            log.warn("authentication token expired");
            return false;
        }

        if (jwtService.isTokenSubjectEmpty(token)) {
            log.warn("authentication token alies empty");
            return false;
        }

        return true;
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