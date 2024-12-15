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

    @ManyToOne(fetch = FetchType.EAGER)
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

    private Double GK;

    private Double CK;

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
    }

    public SheetMark(Student student, Teacher teacher, CourseClass courseClass) {
        this();
        this.student = student;
        this.teacher = teacher;
        this.courseClass = courseClass;
    }

    public SheetMark(Student student, Teacher teacher, CourseClass courseClass, List<Double> BT, List<Double> TN,
            List<Double> BTL, Double GK, Double CK) {
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

    public void setBT(List<Double> BT) {
        this.BT = BT;
        updateFinalMark();
    }

    public void setTN(List<Double> TN) {
        this.TN = TN;
        updateFinalMark();
    }

    public void setBTL(List<Double> BTL) {
        this.BTL = BTL;
        updateFinalMark();
    }

    public void setGK(Double GK) {
        this.GK = GK;
        updateFinalMark();
    }

    public void setCK(Double CK) {
        this.CK = CK;
        updateFinalMark();
    }

    // Hàm cập nhật finalMark
    public void updateFinalMark() {
        // Kiểm tra xem course có tồn tại hay không
        Course course = courseClass.getCourse();

        if (course == null) {
            this.finalMark = null; // Không thể tính nếu không có thông tin course
            return;
        }

        // Lấy hệ số từ course
        Integer coeffTN = course.getCoefficient_of_TN();
        Integer coeffBT = course.getCoefficient_of_BT();
        Integer coeffBTL = course.getCoefficient_of_BTL();
        Integer coeffGK = course.getCoefficient_of_GK();
        Integer coeffCK = course.getCoefficient_of_CK();

        // Kiểm tra xem tất cả hệ số có hợp lệ hay không
        if (coeffTN == null || coeffBT == null || coeffBTL == null || coeffGK == null || coeffCK == null) {
            this.finalMark = null; // Nếu thiếu hệ số nào đó
            return;
        }

        // Tính tổng hệ số
        int totalCoefficient = coeffTN + coeffBT + coeffBTL + coeffGK + coeffCK;

        if (totalCoefficient == 0) {
            this.finalMark = null; // Tránh chia cho 0
            return;
        }

        // Tính điểm trung bình của các danh sách
        double btAvg = BT.isEmpty() ? 0 : BT.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double tnAvg = TN.isEmpty() ? 0 : TN.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double btlAvg = BTL.isEmpty() ? 0 : BTL.stream().mapToDouble(Double::doubleValue).average().orElse(0);

        // Kiểm tra điểm GK và CK có null không
        if (GK == null || CK == null) {
            this.finalMark = null; // Nếu thiếu GK hoặc CK
            return;
        }

        // Tính finalMark dựa trên các hệ số
        this.finalMark = (coeffTN * tnAvg + coeffBT * btAvg + coeffBTL * btlAvg + coeffGK * GK + coeffCK * CK)
                / totalCoefficient;

        if (this.finalMark < 4.0) {
            this.sheetMarkStatus = SheetMarkStatus.Completed_in_fail;
        } else {
            this.sheetMarkStatus = SheetMarkStatus.Completed_in_pass;
        }

        // Cập nhật thời gian sửa đổi
        setUpdatedAt();
    }

}
