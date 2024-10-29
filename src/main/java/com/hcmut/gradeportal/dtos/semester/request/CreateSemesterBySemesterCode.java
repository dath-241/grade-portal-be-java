package com.hcmut.gradeportal.dtos.semester.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSemesterBySemesterCode {
    @NotBlank
    private String semesterCode;
}
