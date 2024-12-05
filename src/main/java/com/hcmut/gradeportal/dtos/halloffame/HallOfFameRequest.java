package com.hcmut.gradeportal.dtos.halloffame;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HallOfFameRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;
    
    private int noOfStudents;
}
