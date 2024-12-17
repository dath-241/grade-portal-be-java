package com.hcmut.gradeportal.dtos.hall_of_fame;

import java.util.List;

import com.hcmut.gradeportal.dtos.sheetMark.SheetMarkDtoForHallOfFame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopGradeForCourse {
    private String courseCode;
    private String courseName;
    private String credit;
    private String semesterCode;
    List<SheetMarkDtoForHallOfFame> sheetMarks;
}
