package com.hcmut.gradeportal.dtos.teacher.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTeacherRequest {
    @NotBlank
    private String userId;
    private String newEmail;
    private String newFamilyName;
    private String newGivenName;
    private String newPhone;
    private String newFaculty;
    private String newTeacherId;
}