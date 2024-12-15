package com.hcmut.gradeportal.repositories;

import java.util.List;
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

        // Sửa lại để sử dụng id.<field>
        Optional<CourseClass> findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                        String courseCode, String semesterCode, String className);

        CourseClass findById_CourseCodeAndId_SemesterCodeAndId_ClassNameAndClassStatus(
                        String courseCode, String semesterCode, String className, ClassStatus classStatus);

        List<CourseClass> findByTeacher_Id(String teacherId); // Nếu Teacher có trường "id"

        @Modifying
        @Transactional
        void deleteById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                        String courseCode, String semesterCode, String className);
}
