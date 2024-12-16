package com.hcmut.gradeportal.dtos.hall_of_fame;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllHallOfFameRequest {
    @NotBlank
    private String semesterCode;
}
