package com.hcmut.gradeportal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
public class Teacher extends User {
    
    private String teacherId;

    // Constructors
    public Teacher() {
        super();
    }

    public Teacher(String teacherId) {
        super();
        this.teacherId = teacherId;
    }
    
    // Getters and setters

}