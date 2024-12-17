package com.hcmut.gradeportal.dtos.courseClass.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {
    @NotNull
    private String studentId;

    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String className;

}
