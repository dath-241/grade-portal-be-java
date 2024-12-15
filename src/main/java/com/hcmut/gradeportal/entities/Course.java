package com.hcmut.gradeportal.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Basic;
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

    @NotNull
    @Basic(fetch = jakarta.persistence.FetchType.EAGER) // Đảm bảo luôn tải khi truy vấn
    private Integer coefficient_of_TN;

    @NotNull
    @Basic(fetch = jakarta.persistence.FetchType.EAGER) // Đảm bảo luôn tải khi truy vấn
    private Integer coefficient_of_BT;

    @NotNull
    @Basic(fetch = jakarta.persistence.FetchType.EAGER) // Đảm bảo luôn tải khi truy vấn
    private Integer coefficient_of_BTL;

    @NotNull
    @Basic(fetch = jakarta.persistence.FetchType.EAGER) // Đảm bảo luôn tải khi truy vấn
    private Integer coefficient_of_GK;

    @NotNull
    @Basic(fetch = jakarta.persistence.FetchType.EAGER) // Đảm bảo luôn tải khi truy vấn
    private Integer coefficient_of_CK;

    // No-args constructor
    public Course() {
    }

    // All-args constructor
    public Course(String courseCode, String courseName, Integer credit, Integer coefficient_of_TN,
            Integer coefficient_of_BT, Integer coefficient_of_BTL, Integer coefficient_of_GK,
            Integer coefficient_of_CK) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.coefficient_of_TN = coefficient_of_TN;
        this.coefficient_of_BT = coefficient_of_BT;
        this.coefficient_of_BTL = coefficient_of_BTL;
        this.coefficient_of_GK = coefficient_of_GK;
        this.coefficient_of_CK = coefficient_of_CK;
    }
}
