package com.hcmut.gradeportal.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.ClassStatus;

public class CourseClassSpecification {

    // Kiểm tra courseCode trong EmbeddedId
    public static Specification<CourseClass> hasCourseCode(String courseCode) {
        return (root, query, builder) -> (courseCode == null || courseCode.isEmpty()) ? null
                : builder.equal(root.get("id").get("courseCode"), courseCode);
    }

    // Kiểm tra semesterCode trong EmbeddedId
    public static Specification<CourseClass> hasSemesterCode(String semesterCode) {
        return (root, query, builder) -> (semesterCode == null || semesterCode.isEmpty()) ? null
                : builder.equal(root.get("id").get("semesterCode"), semesterCode);
    }

    // Kiểm tra className trong EmbeddedId
    public static Specification<CourseClass> hasClassName(String className) {
        return (root, query, builder) -> (className == null || className.isEmpty()) ? null
                : builder.equal(root.get("id").get("className"), className);
    }

    // Kiểm tra teacherId
    public static Specification<CourseClass> hasTeacherId(String teacherId) {
        return (root, query, builder) -> (teacherId == null || teacherId.isEmpty()) ? null
                : builder.equal(root.get("teacher").get("id"), teacherId);
    }

    // Kiểm tra classStatus
    public static Specification<CourseClass> hasClassStatus(ClassStatus classStatus) {
        return (root, query, builder) -> classStatus == null ? null
                : builder.equal(root.get("classStatus"), classStatus);
    }

    // Kiểm tra sự tồn tại của student trong listOfStudents
    public static Specification<CourseClass> hasStudent(Student student) {
        return (root, query, builder) -> (student == null) ? null
                : builder.isMember(student, root.get("listOfStudents"));
    }
}
