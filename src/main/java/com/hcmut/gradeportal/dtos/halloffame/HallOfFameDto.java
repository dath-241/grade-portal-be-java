package com.hcmut.gradeportal.dtos.halloffame;

import java.util.List;

public record HallOfFameDto(
    String familyName,
    String givenName,
    String email,
    String courseCode,
    String semesterCode,
    String className,
    List<Double> BT,
    List<Double> TN,
    List<Double> BTL,
    List<Double> GK,
    List<Double> CK,
    Double finalMark) {
    
}
