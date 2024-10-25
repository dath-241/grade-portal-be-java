package com.hcmut.gradeportal.dtos.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Student;

@Component
public class StudentDtoConverter {
    public StudentDtoConverter() {
    }

    public StudentDto convert(Student from) {
        return new StudentDto(
                from.getId(),
                from.getRole(),
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getPhone(),
                from.getFaculty(),
                from.getStudentId(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getLastLogin());
    }

    public List<StudentDto> convert(List<Student> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<StudentDto> convert(Optional<Student> from) {
        return from.map(this::convert);
    }
}
