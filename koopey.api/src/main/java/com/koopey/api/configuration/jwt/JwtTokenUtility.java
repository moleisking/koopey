package com.koopey.api.configuration.jwt;

import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.entity.User;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtility implements Serializable {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;

    @Autowired
    CustomProperties customProperties;

    public String extractTokenFromBearer(String bearerAuthenticationHeader){

        if (bearerAuthenticationHeader.startsWith("Bearer ")){
            return  bearerAuthenticationHeader.replaceFirst("Bearer ", "");
        } else {
            return "";
        }
    }

    public UUID getIdFromAuthenticationHeader(String authenticationHeader) {

        return UUID.fromString(getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getId));
    }

    public UUID getIdFromToken(String token) {

        return UUID.fromString(getClaimFromToken(token, Claims::getId));
    }

    public UUID getAliasFromAuthenticationHeader(String authenticationHeader) {

        return UUID.fromString(getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getSubject));
    }

    public String getAliasFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Date getExpirationDateFromAuthenticationHeader(String authenticationHeader) {

        return getClaimFromToken(extractTokenFromBearer(authenticationHeader), Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(customProperties.getJwtKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {

        return doGenerateToken(user.getUsername(), user.getId());
    }

    private String doGenerateToken(String alias, UUID id) {

        Claims claims = Jwts.claims().setSubject(alias).setId(id.toString());
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));

        String token = Jwts.builder().setClaims(claims).setIssuer(customProperties.getJwtIssuer())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, customProperties.getJwtKey()).compact();
        
        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String alias = getAliasFromToken(token);
        
        if (alias.equals(userDetails.getUsername()) && !isTokenExpired(token)) {            
            return true;
        } else {
            log.info("Token not valid for: {}", alias);
            return false;
        }
    }
}