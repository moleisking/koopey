package com.koopey.api.service;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.JwtException;
import com.koopey.api.service.impl.IJwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {

    @Autowired
    CustomProperties customProperties;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setExpiration( new Date(customProperties.getJwtExpire()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetails.getUsername())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws JwtException {
        try {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException |
                 UnsupportedJwtException ex) {
            throw new JwtException(ex.getMessage());
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(customProperties.getJwtKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

   /* public byte[] getDecoder(){
        return Decoders.BASE64.decode(customProperties.getJwtKey());
    }*/

    public UUID getAliasFromAuthenticationHeader(String authenticationHeader) /*throws JwtException*/ {

        return UUID.fromString(extractClaim(extractTokenFromBearer(authenticationHeader), Claims::getSubject));
    }

    public String extractTokenFromBearer(String bearerAuthenticationHeader) {

        if (bearerAuthenticationHeader.startsWith("Bearer ")) {
            return bearerAuthenticationHeader.replaceFirst("Bearer ", "");
        } else {
            return "";
        }
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


}
