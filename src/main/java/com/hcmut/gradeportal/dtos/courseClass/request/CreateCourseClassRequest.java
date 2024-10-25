package com.hcmut.gradeportal.dtos.courseClass.request;

import javax.validation.constraints.NotBlank;

import com.hcmut.gradeportal.entities.enums.ClassStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCourseClassRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String className;

    @NotBlank
    private String teacherId;

    private ClassStatus classStatus;
}
