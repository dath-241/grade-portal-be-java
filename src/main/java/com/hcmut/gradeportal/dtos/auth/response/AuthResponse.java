package com.hcmut.gradeportal.dtos.auth.response;

import com.hcmut.gradeportal.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse<T> {
    private String token;
    private T user;
    private Role role;
}
