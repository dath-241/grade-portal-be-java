package com.hcmut.gradeportal.dtos.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkStudentResponse {
    private String email;
    private Integer status;
    private String message;
}
