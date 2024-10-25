package com.hcmut.gradeportal.entities;

import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    private ClassStatus classStatus;

    // No-args constructor
    public CourseClass() {
        this.classStatus = ClassStatus.inProgress;
        this.listOfStudents = new ArrayList<>();
    }

    public CourseClass(String courseCode, String semesterCode, String className) {
        this();
        this.courseCode = courseCode;
        this.semesterCode = semesterCode;
        this.className = className;
    }

}
