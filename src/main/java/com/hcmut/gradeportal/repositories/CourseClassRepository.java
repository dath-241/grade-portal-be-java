package com.hcmut.gradeportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, CourseClassId> {

}
