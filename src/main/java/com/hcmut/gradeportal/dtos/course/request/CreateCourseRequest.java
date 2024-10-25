package com.hcmut.gradeportal.dtos.course.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCourseRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String courseName;

    @Min(0)
    private Integer credit;
}
