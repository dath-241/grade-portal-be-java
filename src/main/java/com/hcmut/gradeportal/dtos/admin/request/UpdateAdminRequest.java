package com.hcmut.gradeportal.dtos.admin.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminRequest {
    @NotBlank
    private String adminId;
    private String newEmail;
    private String newFamilyName;
    private String newGivenName;
    private String newPhone;
    private String newFaculty;
}
