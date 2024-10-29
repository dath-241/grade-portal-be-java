package com.hcmut.gradeportal.dtos.semester.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkSemesterResponse {
    private String semesterCode;
    private Integer status;
    private String message;
}
