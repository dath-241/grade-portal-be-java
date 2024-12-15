package com.hcmut.gradeportal.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
public class Student extends User {

    private String studentId;

    @ManyToMany(mappedBy = "listOfStudents", fetch = FetchType.EAGER)
    private List<CourseClass> listOfCourseClasses = new ArrayList<>();

    // Constructors
    public Student() {
        super();
    }

    public Student(String studentId) {
        super();
        this.studentId = studentId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        this.setUpdatedAt();
    }

    public List<CourseClass> getListOfCourseClasses() {
        return this.listOfCourseClasses;
    }

    public void setListOfCourseClasses(List<CourseClass> listOfCourseClasses) {
        this.listOfCourseClasses = listOfCourseClasses;
        this.setUpdatedAt();
    }

}
