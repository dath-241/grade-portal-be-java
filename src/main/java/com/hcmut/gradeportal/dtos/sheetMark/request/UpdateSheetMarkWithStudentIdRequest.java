package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSheetMarkWithStudentIdRequest {
    @NotBlank
    String studentId;

    @NotBlank
    String courseCode;

    @NotBlank
    String semesterCode;

    @NotBlank
    String className;

    @NotNull
    List<Double> BT;

    @NotNull
    List<Double> TN;

    @NotNull
    List<Double> BTL;

    @NotNull
    List<Double> GK;

    @NotNull
    List<Double> CK;

    SheetMarkStatus sheetMarkStatus;
}
