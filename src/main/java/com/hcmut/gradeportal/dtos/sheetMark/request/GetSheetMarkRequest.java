package com.hcmut.gradeportal.dtos.sheetMark.request;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSheetMarkRequest {
    String courseCode;

    String semesterCode;

    String className;

    SheetMarkStatus sheetMarkStatus;
}
