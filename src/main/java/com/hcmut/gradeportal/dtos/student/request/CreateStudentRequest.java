package com.hcmut.gradeportal.dtos.student.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateStudentRequest {
    @NotBlank
    private String email;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName;

    private String studentId;

    private String phone;

    private String faculty;
}
