package com.hcmut.gradeportal.dtos.student.request;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStudentRequest {

    @NotNull
    private String studentId;
    
    private String newEmail;

    private String newFamilyName;

    private String newGivenName;

    private String newPhone;

    private String newFaculty;
}

