package com.hcmut.gradeportal.dtos.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Student;

@Component
public class StudentDtoForHallOfFameConverter {
    public StudentDtoForHallOfFameConverter() {
    }

    public StudentDtoForHallOfFame convert(Student from) {
        return new StudentDtoForHallOfFame(
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getFaculty(),
                from.getStudentId());
    }

    public List<StudentDtoForHallOfFame> convert(List<Student> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<StudentDtoForHallOfFame> convert(Optional<Student> from) {
        return from.map(this::convert);
    }

}
