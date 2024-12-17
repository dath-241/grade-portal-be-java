package com.hcmut.gradeportal.dtos.course.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseRequest {
    private String courseCode;
    private String courseName;
    private Integer credit;
}
