package com.hcmut.gradeportal.dtos.teacher.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkTeacherResponse {
    private String email;
    private Integer status;
    private String message;
}
