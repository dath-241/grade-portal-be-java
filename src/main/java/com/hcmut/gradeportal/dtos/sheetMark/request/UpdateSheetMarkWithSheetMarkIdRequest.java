package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSheetMarkWithSheetMarkIdRequest {
    @NotBlank
    String sheetMarkId;

    List<Double> BT;

    List<Double> TN;

    List<Double> BTL;

    Double GK;

    Double CK;
}
