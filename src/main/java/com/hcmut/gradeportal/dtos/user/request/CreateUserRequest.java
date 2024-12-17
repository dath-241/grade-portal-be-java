package com.hcmut.gradeportal.dtos.user.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private Role role;

    @NotNull
    private String email;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName;

    private String teacherId;

    private String studentId;

    private String phone;

    private String faculty;
}
