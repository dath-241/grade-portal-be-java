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

        // Lấy danh sách SheetMark theo courseCode
        @Query("SELECT sm FROM SheetMark sm WHERE sm.courseClass.id.courseCode = :courseCode")
        List<SheetMark> findByCourseCode(@Param("courseCode") String courseCode);

        // Lấy danh sách SheetMark theo teacherId
        List<SheetMark> findByTeacherId(String teacherId);

        // Tìm SheetMark theo studentId, courseCode, semesterCode, và className
        @Query("SELECT sm FROM SheetMark sm WHERE sm.student.id = :studentId "
                        + "AND sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        Optional<SheetMark> findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                        @Param("studentId") String studentId,
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode,
                        @Param("className") String className);

        @Query("SELECT sm FROM SheetMark sm WHERE sm.student.studentId = :studentId "
                        + "AND sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        Optional<SheetMark> findByStudentStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                        @Param("studentId") String studentId,
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode,
                        @Param("className") String className);

        // Lấy danh sách SheetMark theo studentId
        @Query("SELECT sm FROM SheetMark sm WHERE sm.student.id = :studentId")
        List<SheetMark> findByStudentId(@Param("studentId") String studentId);

        // Lấy danh sách SheetMark theo courseCode, semesterCode và className
        @Query("SELECT sm FROM SheetMark sm WHERE sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        List<SheetMark> findByCourseCodeAndSemesterCodeAndClassName(String courseCode, String semesterCode,
                        String className);

        // Lấy danh sách SheetMark có finalMark khác null theo courseCode và
        // semesterCode
        @Query("SELECT sm FROM SheetMark sm " +
                        "WHERE sm.courseClass.id.courseCode = :courseCode " +
                        "AND sm.courseClass.id.semesterCode = :semesterCode " +
                        "AND sm.finalMark IS NOT NULL")
        List<SheetMark> findByCourseCodeAndSemesterCodeAndFinalMarkNotNull(
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode);

        // Xóa SheetMark theo studentId, courseCode, semesterCode và className
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

        @Modifying
        @Transactional
        @Query("DELETE FROM SheetMark sm WHERE sm.student.id IN :studentIds "
                        + "AND sm.courseClass.id.courseCode = :courseCode "
                        + "AND sm.courseClass.id.semesterCode = :semesterCode "
                        + "AND sm.courseClass.id.className = :className")
        void deleteAllByStudentIdInAndCourseCodeAndSemesterCodeAndClassName(
                        @Param("studentIds") List<String> studentIds,
                        @Param("courseCode") String courseCode,
                        @Param("semesterCode") String semesterCode,
                        @Param("className") String className);

        @Modifying
        @Transactional
        @Query("DELETE FROM SheetMark sm WHERE sm.student.id = :studentId")
        void deleteByStudentId(@Param("studentId") String studentId);
}
