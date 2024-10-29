package com.hcmut.gradeportal.dtos.sheetMark.request;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSheetMarkRequest {
    String studentId;

    String teacherId;
    String courseCode;
    String semesterCode;
    String className;

    SheetMarkStatus sheetMarkStatus;

}
