package com.hcmut.gradeportal.dtos.courseClass.request;

import javax.validation.constraints.NotBlank;

import java.util.List;

import com.hcmut.gradeportal.entities.enums.ClassStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCourseClassRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String className;

    private String teacherId;

    private List<String> studentIds;

    private ClassStatus classStatus;
}
