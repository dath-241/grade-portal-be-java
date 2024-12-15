package com.hcmut.gradeportal.dtos.courseClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.CourseClass;

@Component
public class CourseClassDtoConverter {
    public CourseClassDtoConverter() {
    }

    public CourseClassDto convert(CourseClass from) {
        List<String> students = from.getListOfStudents().stream().map(student -> student.getId())
                .collect(Collectors.toList());

        String teacherId = from.getTeacher() != null ? from.getTeacher().getId() : null;

        return new CourseClassDto(
                from.getId().getCourseCode(),
                from.getId().getSemesterCode(),
                from.getId().getClassName(),
                teacherId,
                students,
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getClassStatus());
    }

    public List<CourseClassDto> convert(List<CourseClass> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<CourseClassDto> convert(Optional<CourseClass> from) {
        return from.map(this::convert);
    }
}
