package com.hcmut.gradeportal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
public class Student extends User {

    private String studentId;

    // Constructors
    public Student() {
        super();
    }

    public Student(String studentId) {
        super();
        this.studentId = studentId;
    }
    // Getters and setters
}
