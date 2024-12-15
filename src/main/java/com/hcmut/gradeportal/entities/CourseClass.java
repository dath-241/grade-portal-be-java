package com.hcmut.gradeportal.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "course_class")
public class CourseClass {

    @EmbeddedId
    private CourseClassId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("courseCode")
    @JoinColumn(name = "courseCode", referencedColumnName = "courseCode")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("semesterCode")
    @JoinColumn(name = "semesterCode", referencedColumnName = "semesterCode")
    private Semester semester;

    @ManyToMany
    @JoinTable(name = "course_class_student", joinColumns = {
            @JoinColumn(name = "courseCode", referencedColumnName = "courseCode"),
            @JoinColumn(name = "semesterCode", referencedColumnName = "semesterCode"),
            @JoinColumn(name = "className", referencedColumnName = "className")
    }, inverseJoinColumns = @JoinColumn(name = "studentId", referencedColumnName = "id"))
    private List<Student> listOfStudents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private Teacher teacher;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    private ClassStatus classStatus;

    // Constructors
    public CourseClass() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public CourseClass(CourseClassId id) {
        this();
        this.id = id;
    }

    // Getters và Setters
    public CourseClassId getId() {
        return id;
    }

    public void setId(CourseClassId id) {
        this.id = id;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
