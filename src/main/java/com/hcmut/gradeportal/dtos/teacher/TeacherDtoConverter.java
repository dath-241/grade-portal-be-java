package com.hcmut.gradeportal.dtos.teacher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Teacher;

@Component
public class TeacherDtoConverter {
    public TeacherDtoConverter() {
    }

    public TeacherDto convert(Teacher from) {
        return new TeacherDto(
                from.getId(),
                from.getRole(), // Truyền trực tiếp Enum Role mà không chuyển thành chuỗi
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getPhone(),
                from.getFaculty(),
                from.getTeacherId(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getLastLogin());
    }

    public List<TeacherDto> convert(List<Teacher> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<TeacherDto> convert(Optional<Teacher> from) {
        return from.map(this::convert);
    }
}
