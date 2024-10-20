package com.hcmut.gradeportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{

    Optional<Teacher> findByTeacherId(String teacherId); 

} 
