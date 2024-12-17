package com.hcmut.gradeportal.dtos.course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Course;

@Component
public class CourseDtoForTeacherConverter {
    public CourseDtoForTeacherConverter() {
    }

    public CourseDtoForTeacher convert(Course from) {
        return new CourseDtoForTeacher(
                from.getCourseCode(),
                from.getCourseName(),
                from.getCredit());
    }

    public List<CourseDtoForTeacher> convert(List<Course> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<CourseDtoForTeacher> convert(Optional<Course> from) {
        return from.map(this::convert);
    }
}
