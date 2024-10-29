package com.hcmut.gradeportal.dtos.sheetMark.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBulkSheetMarkResponse {
    private String studentEmail;
    private Integer status;
    private String message;
}
