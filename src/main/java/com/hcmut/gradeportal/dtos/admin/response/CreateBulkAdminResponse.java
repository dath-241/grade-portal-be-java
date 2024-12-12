package com.hcmut.gradeportal.dtos.admin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkAdminResponse {
    private String email;
    private Integer status;
    private String message;
}
