package com.hcmut.gradeportal.dtos.teacher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcmut.gradeportal.entities.Teacher;

import org.springframework.stereotype.Component;

@Component
public class TeacherDtoForTeacherConverter {
    public TeacherDtoForTeacherConverter() {
    }

    public TeacherDtoForTeacher convert(Teacher from) {
        return new TeacherDtoForTeacher(
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getFaculty(),
                from.getTeacherId());
    }

    public List<TeacherDtoForTeacher> convert(List<Teacher> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<TeacherDtoForTeacher> convert(Optional<Teacher> from) {
        return from.map(this::convert);
    }
}
