package com.hcmut.gradeportal.dtos.courseClass;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.dtos.course.CourseDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForStudent;
import com.hcmut.gradeportal.entities.enums.ClassStatus;

public record CourseClassDtoForStudent(
        CourseDto course,
        String semesterCode,
        String className,
        TeacherDtoForStudent teacher,
        Integer numOfStudents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ClassStatus classStatus) {
}
