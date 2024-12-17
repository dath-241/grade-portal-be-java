package com.hcmut.gradeportal.dtos.hall_of_fame;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHallOfFameRequest {
    @NotBlank
    private String courseCode;

    @NotBlank
    private String semesterCode;

    private Integer noOfRanks;
}
