package com.hcmut.gradeportal.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.entities.enums.Role;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration-time}")
    private String JWT_EXPIRATION_TIME;

    public String generateToken(String userId, Role role) {
        Key key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_EXPIRATION_TIME)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
