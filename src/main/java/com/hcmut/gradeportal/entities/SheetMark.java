package com.hcmut.gradeportal.entities;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import java.time.LocalDateTime;

import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sheetMark")
public class SheetMark {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "courseCode", referencedColumnName = "courseCode"),
            @JoinColumn(name = "semesterCode", referencedColumnName = "semesterCode"),
            @JoinColumn(name = "className", referencedColumnName = "className")
    })
    private CourseClass courseClass;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Double> BT;

    private List<Double> TN;

    private List<Double> BTL;

    private List<Double> GK;

    private List<Double> CK;

    private Double finalMark;

    private SheetMarkStatus sheetMarkStatus;

    // No-args constructor
    public SheetMark() {
        this.id = UUID.randomUUID().toString(); // Tạo ID duy nhất với UUID
        this.sheetMarkStatus = SheetMarkStatus.inProgress;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.BT = new ArrayList<>();
        this.TN = new ArrayList<>();
        this.BTL = new ArrayList<>();
        this.GK = new ArrayList<>();
        this.CK = new ArrayList<>();
    }

    public SheetMark(Student student, Teacher teacher, CourseClass courseClass) {
        this();
        this.student = student;
        this.teacher = teacher;
        this.courseClass = courseClass;
    }

    public SheetMark(Student student, Teacher teacher, CourseClass courseClass, List<Double> BT, List<Double> TN,
            List<Double> BTL, List<Double> GK, List<Double> CK) {
        this();
        this.student = student;
        this.teacher = teacher;
        this.courseClass = courseClass;
        this.BT = BT;
        this.TN = TN;
        this.BTL = BTL;
        this.GK = GK;
        this.CK = CK;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
