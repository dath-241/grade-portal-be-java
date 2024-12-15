package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSheetMarkWithEmailRequest {
    @NotBlank
    String studentEmail;

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

    SheetMarkStatus sheetMarkStatus;
}
