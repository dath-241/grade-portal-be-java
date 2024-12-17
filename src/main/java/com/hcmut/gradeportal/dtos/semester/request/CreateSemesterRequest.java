package com.hcmut.gradeportal.dtos.semester.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSemesterRequest {

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String semesterName;

    @NotBlank
    private String semesterDuration;
}
