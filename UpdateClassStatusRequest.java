package com.hcmut.gradeportal.dtos.courseClass.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateClassStatusRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    @NotBlank
    private String className;
    
}
