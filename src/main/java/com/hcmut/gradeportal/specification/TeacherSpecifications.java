package com.hcmut.gradeportal.specification;

import org.springframework.stereotype.Component;

import org.springframework.data.jpa.domain.Specification;
import com.hcmut.gradeportal.entities.Teacher;

@Component
public class TeacherSpecifications {

    public static Specification<Teacher> hasEmail(String email) {
        return (root, query,
                builder) -> (email != null && email == "") ? builder.like(root.get("email"), "%" + email + "%") : null;
    }

    public static Specification<Teacher> hasFamilyName(String familyName) {
        return (root, query,
                builder) -> (familyName != null && familyName == "")
                        ? builder.like(root.get("familyName"), "%" + familyName + "%")
                        : null;
    }

    public static Specification<Teacher> hasGivenName(String givenName) {
        return (root, query, builder) -> (givenName != null && givenName == "")
                ? builder.like(root.get("givenName"), "%" + givenName + "%")
                : null;
    }

    public static Specification<Teacher> hasPhone(String phone) {
        return (root, query,
                builder) -> (phone != null && phone == "") ? builder.like(root.get("phone"), "%" + phone + "%") : null;
    }

    public static Specification<Teacher> hasFaculty(String faculty) {
        return (root, query, builder) -> (faculty != null && faculty == "")
                ? builder.like(root.get("faculty"), "%" + faculty + "%")
                : null;
    }
}