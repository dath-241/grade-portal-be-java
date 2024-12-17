package com.hcmut.gradeportal.dtos.courseClass;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.course.CourseDtoConverter;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForStudentConverter;
import com.hcmut.gradeportal.entities.CourseClass;

@Component
public class CourseClassDtoForStudentConverter {
    private final CourseDtoConverter courseDtoConverter;
    private final TeacherDtoForStudentConverter teacherDtoForStudentConverter;

    public CourseClassDtoForStudentConverter(CourseDtoConverter courseDtoConverter,
            TeacherDtoForStudentConverter teacherDtoForStudentConverter) {
        this.courseDtoConverter = courseDtoConverter;
        this.teacherDtoForStudentConverter = teacherDtoForStudentConverter;
    }

    public CourseClassDtoForStudent convert(CourseClass from) {
        return new CourseClassDtoForStudent(
                courseDtoConverter.convert(from.getCourse()),
                from.getId().getSemesterCode(),
                from.getId().getClassName(),
                teacherDtoForStudentConverter.convert(from.getTeacher()),
                from.getListOfStudents().size(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getClassStatus());
    }

    public List<CourseClassDtoForStudent> convert(List<CourseClass> from) {
        return from.stream().map(this::convert).toList();
    }
}
