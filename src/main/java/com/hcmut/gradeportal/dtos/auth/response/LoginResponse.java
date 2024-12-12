package com.hcmut.gradeportal.dtos.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String email;
    private String familyName;
    private String givenName;
    private String role;
    private String faculty;
    private LocalDateTime lastLogin;
    private String token;
    private String idToken;
}