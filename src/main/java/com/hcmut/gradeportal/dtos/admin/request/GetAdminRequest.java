package com.hcmut.gradeportal.dtos.admin.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminRequest {
    String email;
    String familyName;
    String givenName;
    String phone;
    String faculty;
}
