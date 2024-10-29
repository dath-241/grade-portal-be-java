package com.hcmut.gradeportal.dtos.admin;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.entities.enums.Role;

public record AdminDto(String id,
        Role role,
        String email,
        String familyName,
        String givenName,
        String phone,
        String faculty,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLogin) {
}
