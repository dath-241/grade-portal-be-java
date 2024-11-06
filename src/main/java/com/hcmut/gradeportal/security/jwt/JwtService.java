package com.hcmut.gradeportal.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.entities.enums.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration-time}")
    private String JWT_EXPIRATION_TIME;

    public String generateToken(String userId, Role role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }
}
