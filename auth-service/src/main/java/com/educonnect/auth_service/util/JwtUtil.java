package com.educonnect.auth_service.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UUID id, String email, String role) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public UUID getIdFromToken(String token) {
        try {
            return UUID.fromString(
                    Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token).getPayload()
                            .getSubject());
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token).getPayload()
                    .get("email", String.class);
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public String getRoleFromToken(String token) {
        try {
            return Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token).getPayload()
                    .get("role", String.class);
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new JwtException("Invalid Authorization header");
    }

}
