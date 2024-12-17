package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSheetMarkWithStudentIdRequest {
    @NotBlank
    String studentId;

    @NotBlank
    String courseCode;

    @NotBlank
    String semesterCode;

    @NotBlank
    String className;

    List<Double> BT;

    List<Double> TN;

    List<Double> BTL;

    Double GK;

    Double CK;
}
