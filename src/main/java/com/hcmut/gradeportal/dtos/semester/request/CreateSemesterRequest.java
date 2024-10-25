package com.hcmut.gradeportal.dtos.semester.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSemesterRequest {

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String semesterName;

    @NotBlank
    private String semesterDuration;
}
