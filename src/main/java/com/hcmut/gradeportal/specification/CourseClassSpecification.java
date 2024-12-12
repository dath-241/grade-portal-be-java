package com.hcmut.gradeportal.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.ClassStatus;

public class CourseClassSpecification {

    public static Specification<CourseClass> hasCourseCode(String courseCode) {
        return (root, query, builder) -> (courseCode == null || courseCode.equals("")) ? null
                : builder.equal(root.get("courseCode"), courseCode);
    }

    public static Specification<CourseClass> hasSemesterCode(String semesterCode) {
        return (root, query, builder) -> (semesterCode == null || semesterCode.equals("")) ? null
                : builder.equal(root.get("semesterCode"), semesterCode);
    }

    public static Specification<CourseClass> hasClassName(String className) {
        return (root, query, builder) -> (className == null || className.equals("")) ? null
                : builder.equal(root.get("className"), className);
    }

    public static Specification<CourseClass> hasTeacherId(String teacherId) {
        return (root, query, builder) -> (teacherId == null || teacherId.equals("")) ? null
                : builder.equal(root.get("teacher").get("id"), teacherId);
    }

    public static Specification<CourseClass> hasClassStatus(ClassStatus classStatus) {
        return (root, query, builder) -> classStatus == null ? null
                : builder.equal(root.get("classStatus"), classStatus);
    }

    public static Specification<CourseClass> hasStudent(Student student) {
        return (root, query, builder) -> {
            if (student == null) {
                return null;
            } else {
                return builder.isMember(student, root.get("listOfStudents"));
            }
        };
    }
}