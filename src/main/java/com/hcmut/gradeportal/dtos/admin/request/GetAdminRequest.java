package com.hcmut.gradeportal.dtos.admin.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAdminRequest {
    String email;
    String familyName;
    String givenName;
    String phone;
    String faculty;
}
