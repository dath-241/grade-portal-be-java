package com.hcmut.gradeportal.dtos.teacher;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.entities.enums.Role;

public record TeacherDto(
        String id,
        Role role, // Giữ kiểu Enum thay vì String
        String email,
        String familyName,
        String givenName,
        String phone,
        String faculty,
        String teacherId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLogin) {
}