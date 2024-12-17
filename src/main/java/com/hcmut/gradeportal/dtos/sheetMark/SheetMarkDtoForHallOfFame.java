package com.hcmut.gradeportal.dtos.sheetMark;

import java.time.LocalDateTime;
import java.util.List;

import com.hcmut.gradeportal.dtos.student.StudentDtoForHallOfFame;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForHallOfFame;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

public record SheetMarkDtoForHallOfFame(
        StudentDtoForHallOfFame student,
        TeacherDtoForHallOfFame teacher,
        String className,
        ClassStatus classStatus,
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
