package com.hcmut.gradeportal.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "semester")
public class Semester {
    @Id
    private String semesterCode;

    @NotBlank
    private String semesterName;

    @NotNull
    private String semesterDuration;

    // No-args constructor
    public Semester() {
    }

    // Constructor có logic tự động tạo semesterName và semesterDuration
    public Semester(String semesterCode) {
        this.semesterCode = semesterCode;
        this.semesterName = generateSemesterName(semesterCode);
        this.semesterDuration = generateSemesterDuration(semesterCode);
    }

    // Phương thức tạo tên học kỳ từ mã học kỳ (semesterCode)
    private String generateSemesterName(String semesterCode) {
        String semesterPart = semesterCode.substring(4, 5); // Lấy phần chỉ học kỳ (1, 2, hoặc 3)
        switch (semesterPart) {
            case "1":
                return "Học kỳ 1";
            case "2":
                return "Học kỳ 2";
            case "3":
                return "Học kỳ hè";
            default:
                return "Không xác định";
        }
    }

    // Phương thức tạo khoảng thời gian học kỳ từ mã học kỳ (semesterCode)
    private String generateSemesterDuration(String semesterCode) {
        String yearPart = semesterCode; // Lấy phần năm (ví dụ: "241" -> 2024)
        int startYear = 2000 + Integer.parseInt(yearPart.substring(2, 4)); // Tạo năm học từ mã
        return startYear + "-" + (startYear + 1);
    }
}
