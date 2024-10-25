package com.hcmut.gradeportal.dtos.teacher.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBulkTeacherReponse {
    private String email;
    private Integer status;
    private String message;
}
