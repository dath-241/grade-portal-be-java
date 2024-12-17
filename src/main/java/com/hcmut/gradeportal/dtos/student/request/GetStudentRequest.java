package com.hcmut.gradeportal.dtos.student.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentRequest {
    String email;
    String familyName;
    String givenName;
    String phone;
    String faculty;
    String studentId;
}
