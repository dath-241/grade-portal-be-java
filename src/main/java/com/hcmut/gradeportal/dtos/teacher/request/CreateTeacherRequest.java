package com.hcmut.gradeportal.dtos.teacher.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTeacherRequest {
    @NotNull
    private String email;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName;

    private String teacherId;
}
