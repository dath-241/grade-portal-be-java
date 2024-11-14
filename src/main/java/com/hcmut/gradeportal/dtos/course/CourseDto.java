package com.hcmut.gradeportal.dtos.course;

public record CourseDto(
                String courseCode,
                String courseName,
                Integer credit,
                Integer coefficient_of_TN,
                Integer coefficient_of_BT,
                Integer coefficient_of_BTL,
                Integer coefficient_of_GK,
                Integer coefficient_of_CK) {

}