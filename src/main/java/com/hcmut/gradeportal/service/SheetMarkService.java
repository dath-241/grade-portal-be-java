package com.hcmut.gradeportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.sheetMark.request.CreateSheetMarkWhenInit;
import com.hcmut.gradeportal.dtos.sheetMark.request.GetSheetMarkRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithSheetMarkIdRequest;
import com.hcmut.gradeportal.dtos.sheetMark.request.UpdateSheetMarkWithStudentIdRequest;
import com.hcmut.gradeportal.dtos.student.response.StudentStatisticsResponse;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.SheetMarkStatus;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.specification.SheetMarkSpecification;

import jakarta.transaction.Transactional;

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
        public List<SheetMark> getSheetMarksBySpecification(GetSheetMarkRequest request, String studentId) {

                Optional<Student> student = studentRepository.findById(studentId);
                if (!student.isPresent()) {
                        throw new IllegalArgumentException("Student not found");
                }

                if (request.getCourseCode() != null && request.getCourseCode() != ""
                                && request.getSemesterCode() != null && request.getSemesterCode() != ""
                                && request.getClassName() != null && request.getClassName() != "") {
                        Optional<CourseClass> courseClass = courseClassRepository
                                        .findById_CourseCodeAndId_SemesterCodeAndId_ClassName(request.getCourseCode(),
                                                        request.getSemesterCode(), request.getClassName());
                        if (!courseClass.isPresent()) {
                                throw new IllegalArgumentException("Course class not found");
                        }
                }

                Specification<SheetMark> spec = Specification
                                .where(SheetMarkSpecification.hasStudentId(studentId))
                                .and(SheetMarkSpecification.hasCourseCode(request.getCourseCode()))
                                .and(SheetMarkSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(SheetMarkSpecification.hasClassName(request.getClassName()))
                                .and(SheetMarkSpecification.hasSheetMarkStatus(request.getSheetMarkStatus()));

                return sheetMarkRepository.findAll(spec);
        }

        public StudentStatisticsResponse getStatistic(String studentId) {
                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                List<SheetMark> sheetMarks = sheetMarkRepository.findByStudentId(studentId);

                // Thống kê
                int totalSubjects = sheetMarks.size();
                long passedSubjects = sheetMarks.stream()
                                .filter(sheetMark -> sheetMark
                                                .getSheetMarkStatus() == SheetMarkStatus.Completed_in_pass)
                                .count();
                long failedSubjects = sheetMarks.stream()
                                .filter(sheetMark -> sheetMark
                                                .getSheetMarkStatus() == SheetMarkStatus.Completed_in_fail)
                                .count();

                double totalFinalMark = 0.0;
                int totalCredits = 0;
                for (SheetMark sheetMark : sheetMarks) {
                        if (sheetMark.getFinalMark() != null) {
                                totalFinalMark += sheetMark.getFinalMark()
                                                * sheetMark.getCourseClass().getCourse().getCredit();
                                totalCredits += sheetMark.getCourseClass().getCourse().getCredit();
                        }
                }

                double gpa = totalCredits == 0 ? 0 : totalFinalMark / totalCredits;

                return new StudentStatisticsResponse(
                                student.getStudentId(),
                                totalSubjects,
                                (int) passedSubjects,
                                (int) failedSubjects,
                                gpa);
        }

        ////////////// Service for post method - create data //////////////
        // Create sheet mark
        @Transactional
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

                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                SheetMark sheetMark = new SheetMark(student, teacher, courseClass, request.getBT(), request.getTN(),
                                request.getBTL(), request.getGK(), request.getCK());

                return sheetMarkRepository.save(sheetMark);

        }

        ////////////// Service for put method - update data //////////////
        // Update sheet mark with student id
        @Transactional
        public SheetMark updateSheetMark(UpdateSheetMarkWithStudentIdRequest request, String teacherId) {
                Student student = studentRepository.findByStudentId(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra nếu đã quá 6 tháng
                if (courseClass.getUpdatedAt().isBefore(LocalDateTime.now().minusMonths(6))) {
                        throw new IllegalStateException("Course class cannot be updated after 6 months.");
                }

                SheetMark sheetMark = sheetMarkRepository.findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                student.getStudentId(), request.getCourseCode(), request.getSemesterCode(),
                                request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Sheet mark not found"));

                if (!sheetMark.getTeacher().getId().equals(teacherId)) {
                        throw new IllegalArgumentException(
                                        "Teacher does not have permission to update this sheet mark");
                }

                boolean updated = false;

                if (request.getBT() != null) {
                        sheetMark.setBT(request.getBT());
                        updated = true;
                }
                if (request.getTN() != null) {
                        sheetMark.setTN(request.getTN());
                        updated = true;
                }
                if (request.getBTL() != null) {
                        sheetMark.setBTL(request.getBTL());
                        updated = true;
                }
                if (request.getGK() != null) {
                        sheetMark.setGK(request.getGK());
                        updated = true;
                }
                if (request.getCK() != null) {
                        sheetMark.setCK(request.getCK());
                        updated = true;
                }

                if (updated) {
                        sheetMark.updateFinalMark();
                        courseClass.setUpdatedAt(); // Chỉ cập nhật thời gian nếu có thay đổi
                        courseClassRepository.save(courseClass);
                }

                return sheetMarkRepository.save(sheetMark);
        }

        @Transactional
        public SheetMark updateSheetMark(UpdateSheetMarkWithSheetMarkIdRequest request, String teacherId) {
                SheetMark sheetMark = sheetMarkRepository.findById(request.getSheetMarkId())
                                .orElseThrow(() -> new IllegalArgumentException("Sheet mark not found"));

                CourseClass courseClass = sheetMark.getCourseClass();

                // Kiểm tra nếu đã quá 6 tháng
                if (courseClass.getUpdatedAt().isBefore(LocalDateTime.now().minusMonths(6))) {
                        throw new IllegalStateException("Course class cannot be updated after 6 months.");
                }

                if (!sheetMark.getTeacher().getId().equals(teacherId)) {
                        throw new IllegalArgumentException(
                                        "Teacher does not have permission to update this sheet mark");
                }

                boolean updated = false;

                if (request.getBT() != null) {
                        sheetMark.setBT(request.getBT());
                        updated = true;
                }
                if (request.getTN() != null) {
                        sheetMark.setTN(request.getTN());
                        updated = true;
                }
                if (request.getBTL() != null) {
                        sheetMark.setBTL(request.getBTL());
                        updated = true;
                }
                if (request.getGK() != null) {
                        sheetMark.setGK(request.getGK());
                        updated = true;
                }
                if (request.getCK() != null) {
                        sheetMark.setCK(request.getCK());
                        updated = true;
                }

                if (updated) {
                        sheetMark.updateFinalMark();
                        courseClass.setUpdatedAt(); // Chỉ cập nhật thời gian nếu có thay đổi
                        courseClassRepository.save(courseClass);
                }

                return sheetMarkRepository.save(sheetMark);
        }

        ////////////// Service for delete method - delete data //////////////

        // Delete all sheet mark of a student by id
        @Transactional
        public void deleteAllSheetMarkOfStudentById(String id) {
                studentRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

                sheetMarkRepository.deleteByStudentId(id);
        }

}
