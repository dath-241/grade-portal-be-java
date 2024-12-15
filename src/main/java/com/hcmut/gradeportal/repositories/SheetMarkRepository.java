package com.hcmut.gradeportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcmut.gradeportal.entities.SheetMark;

import jakarta.transaction.Transactional;

@Repository
public interface SheetMarkRepository extends JpaRepository<SheetMark, String>, JpaSpecificationExecutor<SheetMark> {
        @Query("SELECT sm FROM SheetMark sm WHERE sm.courseClass.id.courseCode = :courseCode")
        List<SheetMark> findByCourseCode(@Param("courseCode") String courseCode);

        List<SheetMark> findByTeacherId(String teacherId);

        @Query("SELECT sm FROM SheetMark sm WHERE sm.student.id = :studentId "
                        + "AND sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        Optional<SheetMark> findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                        @Param("studentId") String studentId,
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode,
                        @Param("className") String className);

        @Query("SELECT sm FROM SheetMark sm WHERE sm.student.id = :studentId")
        List<SheetMark> findByStudentId(@Param("studentId") String studentId);

        @Modifying
        @Transactional
        @Query("DELETE FROM SheetMark sm WHERE sm.student.id = :studentId "
                        + "AND sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        void deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(@Param("studentId") String studentId,
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode,
                        @Param("className") String className);
}
