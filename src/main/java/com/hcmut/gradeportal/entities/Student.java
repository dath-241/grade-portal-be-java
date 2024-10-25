package com.hcmut.gradeportal.entities;

import java.util.List;

import jakarta.persistence.Entity;
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

    @ManyToMany(mappedBy = "listOfStudents")
    private List<CourseClass> listOfCourseClasses;

    // Constructors
    public Student() {
        super();
    }

    public Student(String studentId) {
        super();
        this.studentId = studentId;
    }
}
