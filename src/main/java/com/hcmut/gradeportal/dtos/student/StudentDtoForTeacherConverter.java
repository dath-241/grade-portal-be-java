package com.hcmut.gradeportal.dtos.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Student;

@Component
public class StudentDtoForTeacherConverter {
    public StudentDtoForTeacherConverter() {
    }

    public StudentDtoForTeacher convert(Student from) {
        return new StudentDtoForTeacher(
                from.getId(),
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getFaculty(),
                from.getPhone(),
                from.getStudentId());
    }

    public List<StudentDtoForTeacher> convert(List<Student> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<StudentDtoForTeacher> convert(Optional<Student> from) {
        return from.map(this::convert);
    }
}
