package com.hcmut.gradeportal.entities;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(CourseClassId.class)
@Table(name = "courseClass")
public class CourseClass {
    @Id
    private String courseCode;

    @Id
    private String semesterCode;

    @Id
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseCode", referencedColumnName = "courseCode", insertable = false, updatable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "courseClassStudent", joinColumns = {
            @JoinColumn(name = "courseCode", referencedColumnName = "courseCode"),
            @JoinColumn(name = "semesterCode", referencedColumnName = "semesterCode"),
            @JoinColumn(name = "className", referencedColumnName = "className")
    }, inverseJoinColumns = @JoinColumn(name = "studentId", referencedColumnName = "id"))
    private List<Student> listOfStudents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    private ClassStatus classStatus;

    // No-args constructor
    public CourseClass() {
        this.classStatus = ClassStatus.inProgress;
        this.listOfStudents = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public CourseClass(String courseCode, String semesterCode, String className) {
        this();
        this.courseCode = courseCode;
        this.semesterCode = semesterCode;
        this.className = className;
    }

    // Getters and Setters

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        this.updatedAt = LocalDateTime.now();
    }

    public String getSemesterCode() {
        return this.semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
        this.updatedAt = LocalDateTime.now();
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
        this.updatedAt = LocalDateTime.now();
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
        this.updatedAt = LocalDateTime.now();
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        this.updatedAt = LocalDateTime.now();
    }

    public List<Student> getListOfStudents() {
        return this.listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public ClassStatus getClassStatus() {
        return this.classStatus;
    }

    public void setClassStatus(ClassStatus classStatus) {
        this.classStatus = classStatus;
        this.updatedAt = LocalDateTime.now();
    }

}
