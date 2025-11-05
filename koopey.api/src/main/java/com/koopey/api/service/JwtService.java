package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.JwtException;
import com.koopey.api.service.impl.IJwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.security.Key;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService implements IJwtService {

    @Autowired
    CustomProperties customProperties;

    private Claims extractAllClaims(String token) throws JwtException {
        try {
            return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException eje) {
            log.warn("Jwt claim expired",eje.getMessage());
         //  throw new JwtException(ex.getMessage());
        } catch ( IllegalArgumentException | MalformedJwtException | SignatureException |
                UnsupportedJwtException ex) {
            log.warn("Jwt claim corruption",ex.getMessage());
            //  throw new JwtException(ex.getMessage());
        }
        return Jwts.claims().empty().build();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Date extractExpiration(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return Objects.requireNonNullElseGet(date, () -> new GregorianCalendar(1700, Calendar.JANUARY, 1).getTime());
    }

    private String extractSubject(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        return Objects.requireNonNullElseGet(subject, () -> "");
    }

    public String extractTokenFromBearer(String bearerAuthenticationHeader) {

        if (bearerAuthenticationHeader.startsWith("Bearer ")) {
            return bearerAuthenticationHeader.replaceFirst("Bearer ", "");
        } else {
            return "";
        }
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenSubjectEmpty(String token) {
        return StringUtils.isEmpty(extractSubject(token));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setExpiration( new Date(customProperties.getJwtExpire()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetails.getUsername())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String getAliasFromToken(String token) throws JwtException {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (JwtException ex) {
            throw new JwtException(ex.getMessage());
        }
    }

    public UUID getIdFromAuthenticationHeader(String authenticationHeader) throws JwtException {
        return UUID.fromString(extractClaim(extractTokenFromBearer(authenticationHeader), Claims::getId));
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(customProperties.getJwtKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
