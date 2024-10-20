package com.hcmut.gradeportal.dtos.admin.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAdminRequest {
    @NotNull
    private String email;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName;
}
