package com.hcmut.gradeportal.dtos.student.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkStudentReponse {
    private String email;
    private Integer status;
    private String message;
}
