package com.hcmut.gradeportal.dtos.sheetMark.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSheetMarkWithSheetMarkIdRequest {
    @NotBlank
    String sheetMarkId;

    @JsonProperty("BT")
    List<Double> BT;

    @JsonProperty("TN")
    List<Double> TN;

    @JsonProperty("BTL")
    List<Double> BTL;

    @JsonProperty("GK")
    Double GK;

    @JsonProperty("CK")
    Double CK;
}
