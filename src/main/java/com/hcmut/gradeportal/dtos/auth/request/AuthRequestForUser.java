package com.hcmut.gradeportal.dtos.auth.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestForUser {
    @NotBlank
    private String idToken;
}
