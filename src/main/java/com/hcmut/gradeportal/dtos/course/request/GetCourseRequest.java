package com.hcmut.gradeportal.dtos.course.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseRequest {
    private String courseCode;
    private String courseName;
    private Integer credit;
}
