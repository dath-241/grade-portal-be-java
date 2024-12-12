package com.hcmut.gradeportal.dtos.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBulkStudentResponse {
    private String studentId;
    private Integer status;
    private String message;
}
