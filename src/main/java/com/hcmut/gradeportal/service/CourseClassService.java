package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.courseClass.request.AddStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.ChangeTeacherRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequestForStudent;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequestForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.request.GetDetailCourseClassRequestForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.request.RemoveStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateClassStatusRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateStudentsInClassRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.Semester;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.entities.idsOfEntities.CourseClassId;
import com.hcmut.gradeportal.repositories.CourseClassRepository;
import com.hcmut.gradeportal.repositories.CourseRepository;
import com.hcmut.gradeportal.repositories.SemesterRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.specification.CourseClassSpecification;

import jakarta.transaction.Transactional;

@Service
public class CourseClassService {
        private final CourseRepository courseRepository;
        private final CourseClassRepository courseClassRepository;

        private final StudentRepository studentRepository;
        private final TeacherRepository teacherRepository;

        private final SheetMarkRepository sheetMarkRepository;
        private final SemesterRepository semesterRepository;

        public CourseClassService(CourseRepository courseRepository, CourseClassRepository courseClassRepository,
                        StudentRepository studentRepository, TeacherRepository teacherRepository,
                        SheetMarkRepository sheetMarkRepository, SemesterRepository semesterRepository) {
                this.courseRepository = courseRepository;
                this.courseClassRepository = courseClassRepository;
                this.studentRepository = studentRepository;
                this.teacherRepository = teacherRepository;
                this.sheetMarkRepository = sheetMarkRepository;
                this.semesterRepository = semesterRepository;
        }

        ////////////// Service for get method - read data //////////////

        // Lấy ra tất cả các CourseClass trong hệ thống
        public List<CourseClass> getAllCourseClasses() {
                return courseClassRepository.findAll();
        }

        // Lấy ra các CourseClass theo điều kiện
        public List<CourseClass> getCourseClassesBySpecification(GetCourseClassRequest request) {
                Student student = null;
                if (request.getStudentId() != null && !request.getStudentId().isEmpty()) {
                        student = studentRepository.findById(request.getStudentId())
                                        .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                }
                // Sử dụng Specifications để lọc dữ liệu
                Specification<CourseClass> spec = Specification
                                .where(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(CourseClassSpecification.hasClassName(request.getClassName()))
                                .and(CourseClassSpecification.hasTeacherId(request.getTeacherId()))
                                .and(CourseClassSpecification.hasClassStatus(request.getClassStatus()))
                                .and(CourseClassSpecification.hasStudent(student));

                // Truy vấn dữ liệu từ database
                return courseClassRepository.findAll(spec);
        }

        public List<CourseClass> teacherGetClassBySpecification(String teacherId,
                        GetCourseClassRequestForTeacher request) {
                if (teacherId == null || teacherId.isEmpty()) {
                        throw new IllegalArgumentException("Teacher ID cannot be null or empty");
                }

                Optional<Teacher> teacher = teacherRepository.findById(teacherId);
                if (!teacher.isPresent()) {
                        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
                }
                Student student = null;
                if (request.getStudentId() != null && !request.getStudentId().isEmpty()) {
                        student = studentRepository.findById(request.getStudentId())
                                        .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                }
                Specification<CourseClass> spec = Specification
                                .where(CourseClassSpecification.hasTeacherId(teacherId))
                                .and(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(CourseClassSpecification.hasClassName(request.getClassName()))
                                .and(CourseClassSpecification.hasClassStatus(request.getClassStatus()))
                                .and(CourseClassSpecification.hasStudent(student));

                return courseClassRepository.findAll(spec);
        }

        public CourseClass getDetailCourseClass(GetDetailCourseClassRequestForTeacher request, String teacherId) {
                Optional<Teacher> teacher = teacherRepository.findById(teacherId);
                if (!teacher.isPresent()) {
                        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
                }

                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                if (!teacherId.equals(courseClass.getTeacher().getId())) {
                        throw new IllegalArgumentException("Teacher does not teach this class");
                }

                return courseClass;
        }

        public List<CourseClass> studentGetClassBySpecification(String studentId,
                        GetCourseClassRequestForStudent request) {
                if (studentId == null || studentId.isEmpty()) {
                        throw new IllegalArgumentException("Student ID cannot be null or empty");
                }
                Specification<CourseClass> spec = Specification
                                .where(CourseClassSpecification.hasStudent(
                                                studentRepository.findById(studentId)
                                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                                "Student not found with ID: "
                                                                                                + studentId))))
                                .and(CourseClassSpecification.hasCourseCode(request.getCourseCode()))
                                .and(CourseClassSpecification.hasSemesterCode(request.getSemesterCode()))
                                .and(CourseClassSpecification.hasTeacherId(request.getTeacherId()))
                                .and(CourseClassSpecification.hasClassName(request.getClassName()))
                                .and(CourseClassSpecification.hasClassStatus(request.getClassStatus()));

                return courseClassRepository.findAll(spec);
        }

        // get course class by student id

        public List<CourseClass> getClassByStudentId(String studentId) {
                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + studentId));

                return student.getListOfCourseClasses();
        }

        // get course class by teacher id
        public List<CourseClass> getClassByTeacherId(String teacherId) {
                if (teacherId == null || teacherId.isEmpty()) {
                        throw new IllegalArgumentException("Teacher ID cannot be null or empty");
                }

                return courseClassRepository.findByTeacher_Id(teacherId);
        }

        public List<CourseClass> getClassByTeacherUserId(String UserId) {
                if (UserId == null || UserId.isEmpty()) {
                        throw new IllegalArgumentException("Teacher ID cannot be null or empty");
                }

                List<CourseClass> courseClasses = courseClassRepository.findAll();
                List<CourseClass> filteredClasses = new ArrayList<>();

                for (CourseClass courseClass : courseClasses) {
                        if (UserId.equals(courseClass.getTeacher().getId())) {
                                filteredClasses.add(courseClass);
                        }
                }

                if (filteredClasses.isEmpty()) {
                        throw new IllegalArgumentException("No classes found for the given teacher ID");
                }
                return filteredClasses;
        }

        // get student info by course class
        public List<Student> getStudentsByTeacherClass(String teacherId, String courseCode, String semesterCode,
                        String className) {
                Teacher teacher = teacherRepository.findById(teacherId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Teacher not found with ID: " + teacherId));
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                courseCode, semesterCode, className)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));
                if (!courseClass.getTeacher().equals(teacher)) {
                        throw new IllegalArgumentException("Teacher does not teach this class");
                }
                return courseClass.getListOfStudents();
        }

        // get teacher info by course class
        public Teacher getTeacherByClass(String courseCode, String semesterCode, String className) {
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                courseCode, semesterCode, className)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));
                return courseClass.getTeacher();
        }

        ////////////// Service for post method - create data //////////////
        // Hàm này dùng khi tạo mới CourseClass, lúc này teacherId sẽ là ID của
        // Teacher-User
        // Hàm này sẽ được controller gọi
        @Transactional
        public CourseClass createCourseClass(CreateCourseClassRequest createCourseClassRequest) {
                // Tạo composite key từ request
                CourseClassId courseClassId = new CourseClassId(
                                createCourseClassRequest.getCourseCode(),
                                createCourseClassRequest.getSemesterCode(),
                                createCourseClassRequest.getClassName());

                // Kiểm tra xem lớp học đã tồn tại chưa
                if (courseClassRepository.existsById(courseClassId)) {
                        throw new IllegalArgumentException("Course class already exists");
                }

                // Lấy thông tin Course và Semester
                Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

                Semester semester = semesterRepository.findBySemesterCode(createCourseClassRequest.getSemesterCode())
                                .orElseThrow(() -> new IllegalArgumentException("Semester not found"));

                // Tạo đối tượng CourseClass mới
                CourseClass newClass = new CourseClass();
                newClass.setId(courseClassId);
                newClass.setCourse(course);
                newClass.setSemester(semester);
                newClass.setClassStatus(
                                createCourseClassRequest.getClassStatus() != null
                                                ? createCourseClassRequest.getClassStatus()
                                                : ClassStatus.inProgress);

                // Gán giáo viên nếu có
                if (createCourseClassRequest.getTeacherId() != null
                                && !createCourseClassRequest.getTeacherId().isEmpty()) {
                        Teacher teacher = teacherRepository.findById(createCourseClassRequest.getTeacherId())
                                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
                        newClass.setTeacher(teacher);
                }

                // Lưu lớp học mới
                courseClassRepository.save(newClass);

                // Gán danh sách sinh viên nếu có
                if (createCourseClassRequest.getStudentIds() != null) {
                        List<Student> students = studentRepository
                                        .findAllById(createCourseClassRequest.getStudentIds());

                        // Kiểm tra có sinh viên nào không tồn tại
                        if (students.size() != createCourseClassRequest.getStudentIds().size()) {
                                throw new IllegalArgumentException("Some student IDs are invalid.");
                        }

                        newClass.setListOfStudents(students);
                }

                // Tạo SheetMark cho mỗi sinh viên nếu có giáo viên
                if (newClass.getTeacher() != null) {
                        for (Student student : newClass.getListOfStudents()) {
                                SheetMark sheetMark = new SheetMark(student, newClass.getTeacher(), newClass);
                                sheetMarkRepository.save(sheetMark);
                        }
                }

                return newClass;
        }

        ////////////// Service for put method - update data //////////////

        // Thay đổi giáo viên của lớp học
        @Transactional
        public CourseClass changeTeacherOfCourseClass(ChangeTeacherRequest request) {
                // Tạo composite key cho CourseClass
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());

                // Tìm kiếm CourseClass
                CourseClass courseClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Tìm kiếm Teacher
                Teacher teacher = teacherRepository.findById(request.getTeacherId())
                                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                // Gán giáo viên mới cho CourseClass
                courseClass.setTeacher(teacher);
                courseClassRepository.save(courseClass);

                // Lấy danh sách SheetMark hiện tại một lần duy nhất
                List<SheetMark> sheetMarks = sheetMarkRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                courseClassId.getCourseCode(),
                                courseClassId.getSemesterCode(),
                                courseClassId.getClassName());

                // Tạo Map để truy cập nhanh các SheetMark hiện tại
                Map<String, SheetMark> sheetMarkMap = sheetMarks.stream()
                                .collect(Collectors.toMap(sm -> sm.getStudent().getId(), sm -> sm));

                // Cập nhật hoặc tạo mới SheetMark cho mỗi sinh viên
                for (Student student : courseClass.getListOfStudents()) {
                        SheetMark sheetMark = sheetMarkMap.getOrDefault(student.getId(),
                                        new SheetMark(student, teacher, courseClass));
                        sheetMark.setTeacher(teacher);
                        sheetMarkRepository.save(sheetMark);
                }

                return courseClass;
        }

        // Cập nhập danh sách sinh viên trong lớp học
        @Transactional
        public CourseClass updateStudentsOfCourseClass(UpdateStudentsInClassRequest request) {
                // Sử dụng composite key để tìm kiếm CourseClass
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());
                CourseClass courseClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                List<Student> oldStudents = courseClass.getListOfStudents();
                List<String> newStudentIds = request.getNewStudentIds();

                List<Student> newStudents = studentRepository.findAllById(newStudentIds);
                if (newStudents.size() != newStudentIds.size()) {
                        throw new IllegalArgumentException("Some student IDs are invalid.");
                }

                List<String> removedStudentIds = oldStudents.stream()
                                .filter(student -> !newStudentIds.contains(student.getId()))
                                .map(Student::getId)
                                .toList();

                if (!removedStudentIds.isEmpty()) {
                        sheetMarkRepository.deleteAllByStudentIdInAndCourseCodeAndSemesterCodeAndClassName(
                                        removedStudentIds, courseClassId.getCourseCode(),
                                        courseClassId.getSemesterCode(), courseClassId.getClassName());
                }
                System.out.println("CourseCode: " + request.getCourseCode());

                courseClass.setListOfStudents(newStudents);
                courseClassRepository.save(courseClass);

                if (courseClass.getTeacher() != null) {
                        for (Student newStudent : newStudents) {
                                SheetMark sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                newStudent.getId(),
                                                                courseClassId.getCourseCode(),
                                                                courseClassId.getSemesterCode(),
                                                                courseClassId.getClassName())
                                                .orElseGet(() -> {
                                                        SheetMark newSheetMark = new SheetMark(newStudent,
                                                                        courseClass.getTeacher(), courseClass);
                                                        newStudent.getListOfCourseClasses().add(courseClass);
                                                        studentRepository.save(newStudent);
                                                        return newSheetMark;
                                                });

                                sheetMarkRepository.save(sheetMark);
                        }
                }

                return courseClass;
        }

        // Thêm sinh viên vào lớp học
        public CourseClass addStudentToClass(AddStudentRequest request, String teacherId) {
                // Tìm sinh viên theo Id trong request
                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + request.getStudentId()));

                // Tìm lớp học dựa trên thông tin trong request
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra xem giáo viên có quyền thêm sinh viên vào lớp học không
                if (teacherId != null && !teacherId.isEmpty()) {
                        if (!teacherId.equals(courseClass.getTeacher().getId())) {
                                throw new IllegalArgumentException("Teacher does not teach this class");
                        }
                }

                // Báo lỗi nếu sinh viên đã tham gia vào lớp học
                if (courseClass.getListOfStudents().contains(student)) {
                        throw new IllegalArgumentException("Student is already enrolled in this class");
                }

                if (courseClass.getTeacher() != null) {
                        Optional<SheetMark> sheetMark = sheetMarkRepository
                                        .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                        request.getStudentId(), request.getCourseCode(),
                                                        request.getSemesterCode(),
                                                        request.getClassName());
                        if (!sheetMark.isPresent()) {
                                // Tạo bảng điểm mới cho sinh viên trong lớp học
                                SheetMark newSheetMark = new SheetMark(student, courseClass.getTeacher(), courseClass);
                                sheetMarkRepository.save(newSheetMark);

                                // Thêm sinh viên vào lớp học và cập nhật
                                courseClass.getListOfStudents().add(student);
                                courseClassRepository.save(courseClass);

                                // Thêm lớp học vào danh sách lớp học của sinh viên và cập nhật
                                student.getListOfCourseClasses().add(courseClass);
                                studentRepository.save(student);
                        }

                }

                return courseClass;
        }

        @Transactional
        public CourseClass updateStatusCourseClassForAdmin(UpdateClassStatusRequest request) {
                return updateStatusForClassInternal(request, null);
        }

        @Transactional
        public CourseClass updateStatusCourseClassForTeacher(UpdateClassStatusRequest request, String teacherId) {
                return updateStatusForClassInternal(request, teacherId);
        }

        private CourseClass updateStatusForClassInternal(UpdateClassStatusRequest request, String teacherId) {
                // Tạo composite key
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());

                // Tìm CourseClass
                CourseClass courseClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra teacherId nếu cần
                if (teacherId != null) {
                        if (courseClass.getTeacher() == null || !courseClass.getTeacher().getId().equals(teacherId)) {
                                throw new IllegalArgumentException("Teacher does not teach this class");
                        }
                }

                // Nếu đã Completed thì không cần làm gì thêm
                if (courseClass.getClassStatus() == ClassStatus.Completed) {
                        return courseClass;
                }

                // Lấy tất cả SheetMark cho lớp học này
                List<SheetMark> sheetMarks = sheetMarkRepository.findByCourseCodeAndSemesterCodeAndClassName(
                                courseClassId.getCourseCode(),
                                courseClassId.getSemesterCode(),
                                courseClassId.getClassName());

                // Kiểm tra tất cả SheetMark có FinalMark không null
                boolean isCompleted = sheetMarks.stream()
                                .allMatch(sheetMark -> sheetMark.getFinalMark() != null);

                // Cập nhật trạng thái lớp học
                if (isCompleted) {
                        courseClass.setClassStatus(ClassStatus.Completed);
                        courseClassRepository.save(courseClass);
                }

                return courseClass;
        }

        // Thay đổi course code của lớp học

        //////////// Service for delete method - delete data //////////////
        public CourseClass deleteCourseClass(UpdateClassStatusRequest request) {
                // Sử dụng composite key để tìm kiếm CourseClass
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());
                CourseClass deleteClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Xóa SheetMark và cập nhật danh sách lớp học của sinh viên
                for (Student student : deleteClass.getListOfStudents()) {
                        sheetMarkRepository.deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                        student.getId(),
                                        courseClassId.getCourseCode(),
                                        courseClassId.getSemesterCode(),
                                        courseClassId.getClassName());

                        student.getListOfCourseClasses().remove(deleteClass);
                        studentRepository.save(student);
                }

                // Xóa danh sách sinh viên khỏi CourseClass
                deleteClass.setListOfStudents(new ArrayList<>());
                courseClassRepository.save(deleteClass);

                // Xóa CourseClass
                courseClassRepository.deleteById(courseClassId);

                return deleteClass;
        }

        public CourseClass getclass(String cou, String se, String nam) {
                return courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassNameAndClassStatus(cou, se,
                                nam,
                                ClassStatus.inProgress);
        }

        //////////// Service for delete method - delete data //////////////

        // Xóa sinh viên ra khỏi tất cả các lớp mà sinh viên đang tham gia
        @Transactional
        public void removeStudentFromAllClasses(String studentId) {
                // Tìm sinh viên theo ID
                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + studentId));

                // Lấy tất cả các lớp học của sinh viên
                List<CourseClass> courseClasses = student.getListOfCourseClasses();

                if (!courseClasses.isEmpty()) {
                        // Xóa sinh viên khỏi danh sách lớp học trong bảng quan hệ ManyToMany
                        for (CourseClass courseClass : courseClasses) {
                                courseClass.getListOfStudents().remove(student);
                        }
                        courseClassRepository.saveAll(courseClasses);
                }

                // Xóa tất cả lớp học khỏi danh sách của sinh viên
                student.getListOfCourseClasses().clear();
                studentRepository.save(student);
        }

        // Xóa sinh viên ra khỏi một lớp và đồng thời xóa bảng điểm của sinh viên trong
        // lớp đó
        // Xóa sinh viên ra khỏi một lớp và đồng thời xóa bảng điểm của sinh viên trong
        // lớp đó
        @Transactional
        public CourseClass removeStudentFromClass(RemoveStudentRequest request, String teacherId) {
                // Tìm sinh viên theo Id trong request
                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + request.getStudentId()));

                // Tìm lớp học dựa trên thông tin trong request
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Kiểm tra teacherId nếu cần
                if (teacherId != null) {
                        if (courseClass.getTeacher() == null || !courseClass.getTeacher().getId().equals(teacherId)) {
                                throw new IllegalArgumentException("Teacher does not teach this class");
                        }
                }

                // Báo lỗi nếu sinh viên chưa tham gia lớp học
                if (!courseClass.getListOfStudents().contains(student)) {
                        throw new IllegalArgumentException("Student is not enrolled in this class");
                }

                // Tìm bảng điểm của sinh viên trong lớp học theo thông tin trong request
                SheetMark sheetMark = sheetMarkRepository.findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                request.getStudentId(), request.getCourseCode(), request.getSemesterCode(),
                                request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Sheet mark not found"));

                // Xóa bảng điểm của sinh viên
                sheetMarkRepository.delete(sheetMark);

                // Xóa sinh viên khỏi danh sách sinh viên của lớp học
                if (courseClass.getListOfStudents().remove(student)) {
                        courseClassRepository.save(courseClass);
                }

                // Xóa lớp học khỏi danh sách lớp học của sinh viên
                if (student.getListOfCourseClasses().remove(courseClass)) {
                        studentRepository.save(student);
                }

                return courseClass;
        }

}
