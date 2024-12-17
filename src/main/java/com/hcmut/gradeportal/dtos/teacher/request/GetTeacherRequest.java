package com.hcmut.gradeportal.dtos.teacher.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTeacherRequest {
    String email;
    String familyName;
    String givenName;
    String phone;
    String faculty;
    String teacherId;
}
