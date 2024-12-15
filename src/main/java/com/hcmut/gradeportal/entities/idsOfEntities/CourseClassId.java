package com.hcmut.gradeportal.entities.idsOfEntities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CourseClassId implements Serializable {
    @Column(name = "courseCode")
    private String courseCode;

    @Column(name = "semesterCode")
    private String semesterCode;

    @Column(name = "className")
    private String className;

    // Constructors
    public CourseClassId() {
    }

    public CourseClassId(String courseCode, String semesterCode, String className) {
        this.courseCode = courseCode;
        this.semesterCode = semesterCode;
        this.className = className;
    }

    // Getters, Setters, equals, hashCode
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseClassId that = (CourseClassId) o;
        return courseCode.equals(that.courseCode) &&
                semesterCode.equals(that.semesterCode) &&
                className.equals(that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, semesterCode, className);
    }
}
