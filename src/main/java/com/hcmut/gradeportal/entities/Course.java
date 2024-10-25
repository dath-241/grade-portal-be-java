package com.hcmut.gradeportal.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    private String courseCode;

    @NotBlank
    private String courseName;

    @Min(0)
    private Integer credit;

    // No-args constructor
    public Course() {
    }

    public Course(String courseCode, String courseName, Integer credit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
    }
}
