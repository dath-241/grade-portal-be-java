package com.hcmut.gradeportal.dtos.hall_of_fame;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetHallOfFameRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    private Integer noOfRanks;
}
