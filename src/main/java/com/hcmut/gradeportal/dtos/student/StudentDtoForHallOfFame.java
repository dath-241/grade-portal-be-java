package com.hcmut.gradeportal.dtos.student;

public record StudentDtoForHallOfFame(
        String email,
        String familyName,
        String givenName,
        String faculty,
        String studentId) {
}
