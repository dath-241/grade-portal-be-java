package com.hcmut.gradeportal.dtos.sheetMark;

import java.time.LocalDateTime;
import java.util.List;

import com.hcmut.gradeportal.dtos.course.CourseDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForStudent;
import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

public record SheetMarkDtoForStudent(
        CourseDto course,
        String semesterCode,
        String className,
        TeacherDtoForStudent teacher,
        List<Double> BT,
        List<Double> TN,
        List<Double> BTL,
        Double GK,
        Double CK,
        Double finalMark,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        SheetMarkStatus sheetMarkStatus) {
}
