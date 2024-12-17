package com.hcmut.gradeportal.dtos.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatisticsResponse {
    private String studentId;
    private int totalSubjects;
    private int passedSubjects;
    private int failedSubjects;
    private double gpa;
}
