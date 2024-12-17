package com.hcmut.gradeportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.enums.Role;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmailAndRole(String email, Role student);

    Optional<Student> findByEmail(String email);
}
