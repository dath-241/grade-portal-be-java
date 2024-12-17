package com.hcmut.gradeportal.dtos.courseClass;

import java.time.LocalDateTime;
import java.util.List;

import com.hcmut.gradeportal.dtos.course.CourseDtoForTeacher;
import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForTeacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;

public record CourseClassDetailDtoForTeacher(
        CourseDtoForTeacher course,
        String semesterCode,
        String className,
        List<SheetMarkDtoForTeacher> studentSheetMarks,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ClassStatus classStatus) {
}
