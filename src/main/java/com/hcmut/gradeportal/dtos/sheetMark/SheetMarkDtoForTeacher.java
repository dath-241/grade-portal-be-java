package com.hcmut.gradeportal.dtos.sheetMark;

import java.time.LocalDateTime;
import java.util.List;

import com.hcmut.gradeportal.dtos.student.StudentDtoForTeacher;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoForTeacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

public record SheetMarkDtoForTeacher(
                String sheetMarkId,
                StudentDtoForTeacher student,
                TeacherDtoForTeacher teacher,
                String className,
                ClassStatus classStatus,
                List<Double> BT,
                List<Double> TN,
                List<Double> BTL,
                Double GK,
                Double CK,
                Double finalMark,
                LocalDateTime getCreatedAt,
                LocalDateTime getUpdatedAt,
                SheetMarkStatus sheetMarkStatus) {
}
