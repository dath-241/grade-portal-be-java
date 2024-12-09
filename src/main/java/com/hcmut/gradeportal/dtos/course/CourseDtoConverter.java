package com.hcmut.gradeportal.dtos.course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.entities.Course;

@Component
public class CourseDtoConverter {
    public CourseDtoConverter() {
    }

    public CourseDto convert(Course from) {
        return new CourseDto(
                from.getCourseCode(),
                from.getCourseName(),
                from.getCredit(),
                from.getCoefficient_of_TN(),
                from.getCoefficient_of_BT(),
                from.getCoefficient_of_BTL(),
                from.getCoefficient_of_GK(),
                from.getCoefficient_of_CK());

    }

    public List<CourseDto> convert(List<Course> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<CourseDto> convert(Optional<Course> from) {
        return from.map(this::convert);
    }

}
