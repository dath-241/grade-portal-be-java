package com.hcmut.gradeportal.dtos.course.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkCourseResponse {
    private String courseCode;
    private Integer status;
    private String message;
}
