package com.hcmut.gradeportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {

}
