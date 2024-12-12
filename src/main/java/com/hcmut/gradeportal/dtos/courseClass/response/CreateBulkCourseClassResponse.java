package com.hcmut.gradeportal.dtos.courseClass.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkCourseClassResponse {
    private String courseCode;
    private String semesterCode;
    private String className;
    private Integer status;
    private String message;
}
