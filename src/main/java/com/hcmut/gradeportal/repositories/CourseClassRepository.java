package com.hcmut.gradeportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;

import jakarta.transaction.Transactional;

@Repository
public interface CourseClassRepository
        extends JpaRepository<CourseClass, CourseClassId>, JpaSpecificationExecutor<CourseClass> {

    Optional<CourseClass> findByCourseCodeAndSemesterCodeAndClassName(String courseCode, String semesterCode,
            String className);
    @Modifying
    @Transactional
    void deleteByCourseCodeAndSemesterCodeAndClassName(String courseCode, String semesterCode,String className);

    CourseClass findByCourseCodeAndSemesterCodeAndClassNameAndClassStatus(String courseCode, String semesterCode,
       String className,ClassStatus classStatus);
}
