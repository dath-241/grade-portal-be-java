package com.hcmut.gradeportal.dtos.teacher;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Teacher;

@Component
public class TeacherDtoForStudentConverter {
    public TeacherDtoForStudentConverter() {
    }

    public TeacherDtoForStudent convert(Teacher from) {
        return new TeacherDtoForStudent(
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getFaculty(),
                from.getTeacherId());
    }
}
