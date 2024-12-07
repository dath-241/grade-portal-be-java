package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSheetMarkWhenInit {
    @NotBlank
    String studentId;

    @NotBlank
    String teacherId;

    @NotBlank
    String courseCode;

    @NotBlank
    String semesterCode;

    @NotBlank
    String className;

    List<Double> BT;
    List<Double> TN;
    List<Double> BTL;
    List<Double> GK;
    List<Double> CK;
}
