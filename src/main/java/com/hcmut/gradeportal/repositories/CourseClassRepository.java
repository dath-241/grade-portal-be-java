package com.hcmut.gradeportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;

@Repository
public interface CourseClassRepository
        extends JpaRepository<CourseClass, CourseClassId>, JpaSpecificationExecutor<CourseClass> {

    Optional<CourseClass> findByCourseCodeAndSemesterCodeAndClassName(String courseCode, String semesterCode,
            String className);

}
