package com.hcmut.gradeportal.dtos.course.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {
    @NotBlank
    private String courseCode;

    private String courseName;

    private Integer credit;

    private Integer coefficient_of_TN;

    private Integer coefficient_of_BT;

    private Integer coefficient_of_BTL;

    private Integer coefficient_of_GK;

    private Integer coefficient_of_CK;
}
