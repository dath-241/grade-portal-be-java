package com.hcmut.gradeportal.dtos.teacher;

public record TeacherDtoForStudent(
        String email,
        String familyName,
        String givenName,
        String faculty,
        String teacherId) {

}
