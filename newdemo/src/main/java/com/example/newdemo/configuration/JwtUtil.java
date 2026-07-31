package com.example.newdemo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.newdemo.model.TokenEntity;
import com.example.newdemo.repository.TokenRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
	

	private final String secretKey = "thisisaverylongsecretkeyformyjwt!";
 // Replace with your secret key

    // Generate token using userId and email
    public String generateToken(Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // Add userId to claims
        return createToken(claims, email);
    }

    // Create the JWT token with claims and subject (email)
	private String createToken(Map<String, Object> claims, String subject) {
        long expirationTimeInMillis = 1000 * 60 * 60 * 10;  // Token valid for 10 hours
        System.out.println(expirationTimeInMillis+" "+claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) 
                .compact();
    }
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract email (subject) from the token
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }
// validate token
    public boolean ValidateToken(String token, String email) {
        try {
            final String extractedEmail = extractEmail(token);
            return (extractedEmail.equals(email) && !isTokenExpired(token));
        } catch (Exception e) {
            // Log the exception if necessary
            return false; // Return false if there's an exception
        }
    } 

    public LocalDateTime getExpirationDate(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.toInstant()
                         .atZone(ZoneId.systemDefault())
                         .toLocalDateTime();
    }
 // Check if the token is expired
    public boolean isTokenExpired(String token) {
        final Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    


}
