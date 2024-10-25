package com.hcmut.gradeportal.entities.idsOfEntities;

import java.io.Serializable;
import java.util.Objects;

public class CourseClassId implements Serializable {

    private String courseCode;
    private String semesterCode;
    private String className;

    // Getters and Setters

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

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseClassId that = (CourseClassId) o;
        return Objects.equals(courseCode, that.courseCode) &&
                Objects.equals(semesterCode, that.semesterCode) &&
                Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, semesterCode, className);
    }
}
