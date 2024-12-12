package com.hcmut.gradeportal.dtos.teacher.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBulkTeacherResponse {
    private String teacherId;
    private Integer status;
    private String message;
}
