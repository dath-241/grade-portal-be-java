package com.hcmut.gradeportal.dtos.course.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String courseName;

    @Min(0)
    private Integer credit;

    @Min(0)
    private Integer coefficient_of_TN;

    @Min(0)
    private Integer coefficient_of_BT;

    @Min(0)
    private Integer coefficient_of_BTL;

    @Min(0)
    private Integer coefficient_of_GK;

    @Min(0)
    private Integer coefficient_of_CK;
}
