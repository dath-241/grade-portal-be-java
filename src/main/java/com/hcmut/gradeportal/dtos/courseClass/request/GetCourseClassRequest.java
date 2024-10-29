package com.hcmut.gradeportal.dtos.courseClass.request;

import com.hcmut.gradeportal.entities.enums.ClassStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseClassRequest {
    private String courseCode;
    private String semesterCode;
    private String className;
    private String teacherId;
    private String studentId;
    private ClassStatus classStatus;
}
