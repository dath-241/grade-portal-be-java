package com.hcmut.gradeportal.dtos.teacher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Teacher;

@Component
public class TeacherDtoForHallOfFameConverter {
    public TeacherDtoForHallOfFameConverter() {
    }

    public TeacherDtoForHallOfFame convert(Teacher from) {
        return new TeacherDtoForHallOfFame(
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getFaculty(),
                from.getTeacherId());
    }

    public List<TeacherDtoForHallOfFame> convert(List<Teacher> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<TeacherDtoForHallOfFame> convert(Optional<Teacher> from) {
        return from.map(this::convert);
    }
}
