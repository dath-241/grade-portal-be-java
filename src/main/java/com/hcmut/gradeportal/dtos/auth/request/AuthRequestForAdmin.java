package com.hcmut.gradeportal.dtos.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestForAdmin {
    private String idToken;
}
