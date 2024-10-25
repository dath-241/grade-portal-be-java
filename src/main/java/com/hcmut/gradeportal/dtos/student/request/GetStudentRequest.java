package com.hcmut.gradeportal.dtos.student.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetStudentRequest {
    String email;
    String familyName;
    String givenName;
    String phone;
    String faculty;
    String studentId;
}
