package com.hcmut.gradeportal.dtos.student;

public record StudentDtoForTeacher(
        String id,
        String email,
        String familyName,
        String givenName,
        String faculty,
        String phone,
        String studentId) {

}
