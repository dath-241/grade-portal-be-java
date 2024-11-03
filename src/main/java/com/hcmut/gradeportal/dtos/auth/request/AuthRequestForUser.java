package com.hcmut.gradeportal.dtos.auth.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestForUser {
    @NotBlank
    private String idToken;

    @NotNull
    private Role role;
}
