package com.hcmut.gradeportal.dtos.courseClass;

import java.util.List;
import java.time.LocalDateTime;

import com.hcmut.gradeportal.entities.enums.ClassStatus;

public record CourseClassDto(String courseCode,
                String semesterCode,
                String className,
                String teacherId,
                List<String> studentIds,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                ClassStatus classStatus) {
}
