package com.hcmut.gradeportal.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.sheetMark.request.CreateSheetMarkWhenInit;
import com.hcmut.gradeportal.dtos.sheetMark.request.GetSheetMarkRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithEmailRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithStudentIdRequest;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.specification.SheetMarkSpecification;

@Service
public class SheetMarkService {
        private final StudentRepository studentRepository;
        private final CourseClassRepository courseClassRepository;
        private final TeacherRepository teacherRepository;

        private final SheetMarkRepository sheetMarkRepository;

        public SheetMarkService(SheetMarkRepository sheetMarkRepository, StudentRepository studentRepository,
                        CourseClassRepository courseClassRepository, TeacherRepository teacherRepository) {
                this.sheetMarkRepository = sheetMarkRepository;
                this.studentRepository = studentRepository;
                this.courseClassRepository = courseClassRepository;
                this.teacherRepository = teacherRepository;
        }

        ////////////// Service for get method - read data //////////////
        // Get all sheet mark
        public List<SheetMark> getAllSheetMark() {
                return sheetMarkRepository.findAll();
        }

        // Get sheet mark by student id
        public List<SheetMark> getSheetMarkByStudentId(String studentId) {
                Optional<Student> student = studentRepository.findById(studentId);
                if (!student.isPresent()) {
                        throw new IllegalArgumentException("Student not found");
                }
                return sheetMarkRepository.findByStudentId(studentId);
        }

        // Get sheet mark by specification
        public List<SheetMark> getSheetMarksBySpecification(GetSheetMarkRequest request) {
                if (request.getStudentId() != null && request.getStudentId() != "") {
                        Optional<Student> student = studentRepository.findById(request.getStudentId());
                        if (!student.isPresent()) {
                                throw new IllegalArgumentException("Student not found");
                        }
                }

                if (request.getTeacherId() != null && request.getTeacherId() != "") {
                        Optional<Teacher> teacher = teacherRepository.findById(request.getTeacherId());
                        if (!teacher.isPresent()) {
                                throw new IllegalArgumentException("Teacher not found");
                        }
                }

                if (request.getCourseCode() != null && request.getCourseCode() != ""
                                && request.getSemesterCode() != null && request.getSemesterCode() != ""
                                && request.getClassName() != null && request.getClassName() != "") {
                        Optional<CourseClass> courseClass = courseClassRepository
                                        .findByCourseCodeAndSemesterCodeAndClassName(request.getCourseCode(),
                                                        request.getSemesterCode(), request.getClassName());
                        if (!courseClass.isPresent()) {
                                throw new IllegalArgumentException("Course class not found");
                        }
                }

                Specification<SheetMark> spec = Specification
                                .where(SheetMarkSpecification.hasStudentId(request.getStudentId()))
                                .and(SheetMarkSpecification.hasTeacherId(request.getTeacherId()))
                                .and(SheetMarkSpecification.hasCourseCode(request.getCourseCode()))
                                .and(SheetMarkSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(SheetMarkSpecification.hasClassName(request.getClassName()))
                                .and(SheetMarkSpecification.hasSheetMarkStatus(request.getSheetMarkStatus()));

                return sheetMarkRepository.findAll(spec);
        }

        ////////////// Service for post method - create data //////////////
        // Create sheet mark
        public SheetMark createSheetMark(CreateSheetMarkWhenInit request) {
                Optional<SheetMark> optSheetMark = sheetMarkRepository
                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                request.getStudentId(), request.getCourseCode(),
                                                request.getSemesterCode(), request.getClassName());

                if (optSheetMark.isPresent()) {
                        throw new IllegalArgumentException("Sheet mark already exists");
                }

                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                Teacher teacher = teacherRepository.findById(request.getTeacherId())
                                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                CourseClass courseClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                SheetMark sheetMark = new SheetMark(student, teacher, courseClass, request.getBT(), request.getTN(),
                                request.getBTL(), request.getGK(), request.getCK());

                return sheetMarkRepository.save(sheetMark);

        }

        ////////////// Service for put method - update data //////////////
        // Update sheet mark with email student
        public SheetMark updateSheetMark(UpdateSheetMarkWithEmailRequest request) {
                Student student = studentRepository.findByEmailAndRole(request.getStudentEmail(), Role.STUDENT)
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                CourseClass courseClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra nếu trường updatedAt của CourseClass đã quá 6 tháng
                LocalDateTime sixMonthsAgo = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
                if (courseClass.getUpdatedAt().isBefore(sixMonthsAgo)) {
                        throw new IllegalStateException(
                                        "Course class cannot be updated as it was last updated more than 6 months ago.");
                }

                SheetMark sheetMark = sheetMarkRepository.findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                student.getId(), request.getCourseCode(), request.getSemesterCode(),
                                request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Sheet mark not found"));

                courseClass.setUpdatedAt();
                courseClassRepository.save(courseClass);

                sheetMark.setBT(request.getBT());
                sheetMark.setTN(request.getTN());
                sheetMark.setBTL(request.getBTL());
                sheetMark.setGK(request.getGK());
                sheetMark.setCK(request.getCK());

                if (request.getSheetMarkStatus() != null) {
                        sheetMark.setSheetMarkStatus(request.getSheetMarkStatus());
                }

                sheetMark.setUpdatedAt();

                return sheetMarkRepository.save(sheetMark);
        }

        // Update sheet mark with student id
        public SheetMark updateSheetMark(UpdateSheetMarkWithStudentIdRequest request) {
                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                CourseClass courseClass = courseClassRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra nếu trường updatedAt của CourseClass đã quá 6 tháng
                LocalDateTime sixMonthsAgo = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
                if (courseClass.getUpdatedAt().isBefore(sixMonthsAgo)) {
                        throw new IllegalStateException(
                                        "Course class cannot be updated as it was last updated more than 6 months ago.");
                }

                SheetMark sheetMark = sheetMarkRepository.findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                student.getId(), request.getCourseCode(), request.getSemesterCode(),
                                request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Sheet mark not found"));

                courseClass.setUpdatedAt();
                courseClassRepository.save(courseClass);

                sheetMark.setBT(request.getBT());
                sheetMark.setTN(request.getTN());
                sheetMark.setBTL(request.getBTL());
                sheetMark.setGK(request.getGK());
                sheetMark.setCK(request.getCK());

                if (request.getSheetMarkStatus() != null) {
                        sheetMark.setSheetMarkStatus(request.getSheetMarkStatus());
                }

                sheetMark.setUpdatedAt();

                return sheetMarkRepository.save(sheetMark);

        }

        
        ////////////// Service for delete method - delete data //////////////
        
        // Delete all sheet mark of a student by id
        public void deleteAllSheetMarkOfStudentById(String id) {
                studentRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                
                List<SheetMark> sheetMarks = sheetMarkRepository.findByStudentId(id);
                
                if (sheetMarks != null && !sheetMarks.isEmpty()) {
                        sheetMarkRepository.deleteAll(sheetMarks);
                }
        }
            
}
