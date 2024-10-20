package com.hcmut.gradeportal.dtos.user.request;

import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull
    private Role role;

    @NotNull
    private String email;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName;

    private String teacherId;

    private String studentId;
}
