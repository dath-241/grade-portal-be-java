package com.hcmut.gradeportal.dtos.courseClass.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailCourseClassRequestForTeacher {
    private String courseCode;
    private String semesterCode;
    private String className;
}
