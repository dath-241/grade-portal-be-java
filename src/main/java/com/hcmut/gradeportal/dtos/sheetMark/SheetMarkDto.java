package com.hcmut.gradeportal.dtos.sheetMark;

import java.time.LocalDateTime;
import java.util.List;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

public record SheetMarkDto(
                String id,
                String studentId,
                String studentEmail,
                String teacherId,
                String courseCode,
                String semesterCode,
                String className,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                List<Double> BT,
                List<Double> TN,
                List<Double> BTL,
                List<Double> GK,
                List<Double> CK,
                Double finalMark,
                SheetMarkStatus sheetMarkStatus) {
}
