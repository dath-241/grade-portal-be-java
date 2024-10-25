package com.hcmut.gradeportal.dtos.student;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.entities.enums.Role;

public record StudentDto(
        String id,
        Role role, // Giữ kiểu Enum thay vì String
        String email,
        String familyName,
        String givenName,
        String phone,
        String faculty,
        String studentId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLogin) {
}
