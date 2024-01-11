package com.koopey.api.configuration.jwt;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.JwtException;
import com.koopey.api.model.entity.User;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtility implements Serializable {

    //public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;

    @Autowired
    CustomProperties customProperties;

    public String extractTokenFromBearer(String bearerAuthenticationHeader) {

        if (bearerAuthenticationHeader.startsWith("Bearer ")) {
            return bearerAuthenticationHeader.replaceFirst("Bearer ", "");
        } else {
            return "";
        }
    }

    public UUID getIdFromAuthenticationHeader(String authenticationHeader) throws JwtException {
        return UUID.fromString(getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getId));
    }

    public UUID getIdFromToken(String token) throws JwtException {
        return UUID.fromString(getClaimFromToken(token, Claims::getId));
    }

    public UUID getAliasFromAuthenticationHeader(String authenticationHeader) /*throws JwtException*/ {

        return UUID.fromString(getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getSubject));
    }

    public String getAliasFromToken(String token) throws JwtException {
        try {
        return getClaimFromToken(token, Claims::getSubject);
        } catch (JwtException ex) {
            throw new JwtException(ex.getMessage());
        }
    }

    public Date getExpirationDateFromToken(String token) throws JwtException {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Date getExpirationDateFromAuthenticationHeader(String authenticationHeader) {
        return getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws JwtException {
        try {
          final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (JwtException ex) {
            throw new JwtException(ex.getMessage());
        }
    }

    private Claims getAllClaimsFromToken(String token) throws JwtException {
       // Claims temp = null;
        try {
            return Jwts.parser().setSigningKey(customProperties.getJwtKey()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException |
                 UnsupportedJwtException ex) {
            throw new JwtException(ex.getMessage());
        }

    }

    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {

        return doGenerateToken(user.getAlias(), user.getId());
    }

    private String doGenerateToken(String alias, UUID id) {

        Claims claims = Jwts.claims().setSubject(alias).setId(id.toString());
        claims.put("scopes", List.of(new SimpleGrantedAuthority("USER")));

        return Jwts.builder().setClaims(claims).setIssuer(customProperties.getJwtIssuer())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + customProperties.getJwtExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, customProperties.getJwtKey()).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws JwtException{

        final String alias = getAliasFromToken(token);

        if (alias.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            return true;
        } else {
            log.info("Token not valid for: {}", alias);
            return false;
        }
    }
}