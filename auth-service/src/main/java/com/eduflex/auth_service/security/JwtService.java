package com.eduflex.auth_service.security;


import com.eduflex.auth_service.Enum.UserType;
import com.eduflex.auth_service.dto.UserResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minute
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) throws BadRequestException {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token) throws BadRequestException {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws BadRequestException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws BadRequestException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new BadRequestException("Invalid or expired JWT token");
        }
    }

    public String generateAccessToken(UserResponse user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("type", user.getType());


        return createToken(claims, user.getEmail(), ACCESS_TOKEN_EXPIRATION);
    }

    // Refresh Token
    public String generateRefreshToken(UserResponse user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("type", "refresh");

        return createToken(claims, user.getEmail(), REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) throws BadRequestException {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException e) {
            throw new BadRequestException("Invalid or expired JWT token");
        }
    }

    private boolean isTokenExpired(String token) throws BadRequestException {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws BadRequestException {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) throws BadRequestException {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.get("userId").toString());
    }

}
