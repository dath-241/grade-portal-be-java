package com.hcmut.gradeportal.dtos.courseClass;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.dtos.course.CourseDtoForTeacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;

public record CourseClassDtoForTeacher(
                CourseDtoForTeacher course,
                String semesterCode,
                String className,
                Integer numOfStudents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                ClassStatus classStatus) {
}
