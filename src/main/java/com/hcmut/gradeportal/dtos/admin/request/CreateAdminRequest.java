package com.hcmut.gradeportal.dtos.admin.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAdminRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String familyName;

    @NotBlank
    private String givenName;

    @NotBlank
    private String phone;

    @NotBlank
    private String faculty;
}
