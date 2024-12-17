package com.hcmut.gradeportal.dtos.courseClass;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.course.CourseDtoForTeacherConverter;
import com.hcmut.gradeportal.entities.CourseClass;

@Component
public class CourseClassDtoForTeacherConverter {
    private final CourseDtoForTeacherConverter courseDtoForTeacherConverter;

    public CourseClassDtoForTeacherConverter(CourseDtoForTeacherConverter courseDtoForTeacherConverter) {
        this.courseDtoForTeacherConverter = courseDtoForTeacherConverter;
    }

    public CourseClassDtoForTeacher convert(CourseClass from) {
        return new CourseClassDtoForTeacher(
                courseDtoForTeacherConverter.convert(from.getCourse()),
                from.getId().getSemesterCode(),
                from.getId().getClassName(),
                from.getListOfStudents().size(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getClassStatus());
    }

    public List<CourseClassDtoForTeacher> convert(List<CourseClass> from) {
        return from.stream().map(this::convert).toList();
    }
}
