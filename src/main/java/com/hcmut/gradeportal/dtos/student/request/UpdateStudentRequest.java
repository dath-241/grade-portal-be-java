package com.hcmut.gradeportal.dtos.student.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStudentRequest {

    @NotBlank
    private String userId;

    private String newEmail;

    private String newFamilyName;

    private String newGivenName;

    private String newPhone;

    private String newFaculty;

    private String newStudentId;
}
