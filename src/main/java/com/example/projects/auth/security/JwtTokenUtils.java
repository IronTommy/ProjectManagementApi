package com.example.projects.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final KeyUtils keyUtils;

    public boolean validateToken(String token) {
        return  !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyUtils.getAccessTokenPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(keyUtils.getAccessTokenPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
