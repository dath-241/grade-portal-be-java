package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.dtos.courseClass.request.AddStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.ChangeTeacherRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
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

        public List<CourseClass> teacherGetClassBySpecification(String teacherId, GetCourseClassRequest request) {
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

        public List<CourseClass> studentGetClassBySpecification(String studentId, GetCourseClassRequest request) {
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
                if (studentId == null || studentId.isEmpty()) {
                        throw new IllegalArgumentException("Student ID cannot be null or empty");
                }

                List<CourseClass> courseClasses = courseClassRepository.findAll();
                List<CourseClass> filteredClasses = new ArrayList<>();

                for (CourseClass courseClass : courseClasses) {
                        List<Student> studentsOfClass = courseClass.getListOfStudents();
                        for (Student student : studentsOfClass) {
                                if (studentId.equals(student.getStudentId())) {
                                        filteredClasses.add(courseClass);
                                        break;
                                }
                        }
                }

                if (filteredClasses.isEmpty()) {
                        throw new IllegalArgumentException("No classes found for the given student ID");
                }

                return filteredClasses;
        }

        // get course class by student_user id

        public List<CourseClass> getClassByStudentUserId(String UserId) {
                if (UserId == null || UserId.isEmpty()) {
                        throw new IllegalArgumentException("Student ID cannot be null or empty");
                }

                List<CourseClass> courseClasses = courseClassRepository.findAll();
                List<CourseClass> filteredClasses = new ArrayList<>();

                for (CourseClass courseClass : courseClasses) {
                        List<Student> studentsOfClass = courseClass.getListOfStudents();
                        for (Student student : studentsOfClass) {
                                if (UserId.equals(student.getId())) {
                                        filteredClasses.add(courseClass);
                                        break;
                                }
                        }
                }

                if (filteredClasses.isEmpty()) {
                        throw new IllegalArgumentException("No classes found for the given student ID");
                }
                return filteredClasses;
        }

        // get course class by teacher id

        public List<CourseClass> getClassByTeacherId(String teacherId) {
                if (teacherId == null || teacherId.isEmpty()) {
                        throw new IllegalArgumentException("Teacher ID cannot be null or empty");
                }

                List<CourseClass> courseClasses = courseClassRepository.findAll();
                List<CourseClass> filteredClasses = new ArrayList<>();

                for (CourseClass courseClass : courseClasses) {
                        if (teacherId.equals(courseClass.getTeacher().getTeacherId())) {
                                filteredClasses.add(courseClass);
                        }
                }

                if (filteredClasses.isEmpty()) {
                        throw new IllegalArgumentException("No classes found for the given teacher ID");
                }
                return filteredClasses;
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
        public CourseClass createCourseClass(CreateCourseClassRequest createCourseClassRequest) {
                // Tạo composite key từ request
                CourseClassId courseClassId = new CourseClassId(
                                createCourseClassRequest.getCourseCode(),
                                createCourseClassRequest.getSemesterCode(),
                                createCourseClassRequest.getClassName());

                // Kiểm tra xem lớp học đã tồn tại chưa
                Optional<CourseClass> optCourseClass = courseClassRepository.findById(courseClassId);
                if (optCourseClass.isPresent()) {
                        throw new IllegalArgumentException("Course class already exists");
                }

                Course course = courseRepository.findByCourseCode(createCourseClassRequest.getCourseCode())
                                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

                // Gán thông tin Semester
                Semester semester = semesterRepository.findBySemesterCode(createCourseClassRequest.getSemesterCode())
                                .orElseThrow(() -> new IllegalArgumentException("Semester not found"));

                // Tạo đối tượng CourseClass mới
                CourseClass newClass = new CourseClass();
                newClass.setCourse(course);
                newClass.setSemester(semester); // Gán giá trị semester
                newClass.setId(courseClassId); // Gán composite key
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

                courseClassRepository.save(newClass);

                // Gán danh sách sinh viên nếu có
                List<Student> studentsOfClass = new ArrayList<>();
                if (createCourseClassRequest.getStudentIds() != null) {
                        for (String studentId : createCourseClassRequest.getStudentIds()) {
                                Student student = studentRepository.findById(studentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Student not found with ID: " + studentId));

                                // Kiểm tra xem lớp học đã tồn tại trong danh sách của sinh viên hay chưa
                                if (student.getListOfCourseClasses() == null) {
                                        student.setListOfCourseClasses(new ArrayList<>());
                                }
                                if (!student.getListOfCourseClasses().contains(newClass)) {
                                        student.getListOfCourseClasses().add(newClass);
                                        studentRepository.save(student); // Lưu lại sinh viên
                                }

                                studentsOfClass.add(student);
                        }
                }
                newClass.setListOfStudents(studentsOfClass);

                // Lưu CourseClass
                courseClassRepository.save(newClass);

                // Tạo SheetMark cho mỗi sinh viên nếu có giáo viên
                if (newClass.getTeacher() != null && newClass.getTeacher().getId() != null) {
                        for (Student student : studentsOfClass) {
                                Optional<SheetMark> sheetMark = sheetMarkRepository
                                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                                student.getId(),
                                                                courseClassId.getCourseCode(),
                                                                courseClassId.getSemesterCode(),
                                                                courseClassId.getClassName());

                                if (!sheetMark.isPresent()) {
                                        SheetMark newSheetMark = new SheetMark(student, newClass.getTeacher(),
                                                        newClass);
                                        sheetMarkRepository.save(newSheetMark);
                                }
                        }
                }

                return newClass;
        }

        ////////////// Service for put method - update data //////////////

        // Thay đổi giáo viên của lớp học
        public CourseClass changeTeacherOfCourseClass(ChangeTeacherRequest request) {
                // Tìm kiếm CourseClass bằng composite key
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());
                CourseClass courseClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                // Tìm kiếm Teacher bằng teacherId
                Teacher teacher = teacherRepository.findById(request.getTeacherId())
                                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

                // Gán lại giáo viên cho CourseClass
                courseClass.setTeacher(teacher);
                courseClassRepository.save(courseClass);

                // Cập nhật hoặc tạo mới SheetMark cho tất cả sinh viên trong CourseClass
                for (Student student : courseClass.getListOfStudents()) {
                        SheetMark sheetMark = sheetMarkRepository
                                        .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                        student.getId(),
                                                        courseClassId.getCourseCode(),
                                                        courseClassId.getSemesterCode(),
                                                        courseClassId.getClassName())
                                        .orElseGet(() -> new SheetMark(student, teacher, courseClass)); // Tạo mới nếu
                                                                                                        // không tồn tại

                        // Cập nhật giáo viên cho SheetMark
                        sheetMark.setTeacher(teacher);
                        sheetMarkRepository.save(sheetMark);
                }

                return courseClass;
        }

        // Cập nhập danh sách sinh viên trong lớp học
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

                List<Student> newStudents = newStudentIds.stream()
                                .map(studentId -> studentRepository.findById(studentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Student not found with ID: " + studentId)))
                                .toList();

                for (Student oldStudent : oldStudents) {
                        if (!newStudentIds.contains(oldStudent.getId())) {
                                sheetMarkRepository.deleteByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                oldStudent.getId(),
                                                courseClassId.getCourseCode(),
                                                courseClassId.getSemesterCode(),
                                                courseClassId.getClassName());
                                oldStudent.getListOfCourseClasses().remove(courseClass);
                                studentRepository.save(oldStudent);
                        }
                }

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
        public CourseClass addStudentToClass(AddStudentRequest request) {
                // Tìm sinh viên theo Id trong request
                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + request.getStudentId()));

                // Tìm lớp học dựa trên thông tin trong request
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

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

        // Cập nhập trạng thái của lớp học
        public CourseClass updateStatusCourseClass(UpdateClassStatusRequest request) {
                // Sử dụng composite key để tìm kiếm CourseClass
                CourseClassId courseClassId = new CourseClassId(
                                request.getCourseCode(),
                                request.getSemesterCode(),
                                request.getClassName());
                CourseClass updateClass = courseClassRepository.findById(courseClassId)
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

                if (updateClass.getClassStatus() == ClassStatus.Completed) {
                        return updateClass;
                }

                List<Student> listStudents = updateClass.getListOfStudents();
                boolean isCompleted = true;

                for (Student student : listStudents) {
                        SheetMark sheetMark = sheetMarkRepository
                                        .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                                        student.getId(),
                                                        courseClassId.getCourseCode(),
                                                        courseClassId.getSemesterCode(),
                                                        courseClassId.getClassName())
                                        .orElseThrow(() -> new IllegalArgumentException("SheetMark not found"));

                        if (sheetMark.getFinalMark() == null) {
                                isCompleted = false;
                                break;
                        }
                }

                if (isCompleted) {
                        updateClass.setClassStatus(ClassStatus.Completed);
                        courseClassRepository.save(updateClass);
                }

                return updateClass;
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
        public void removeStudentFromAllClasses(String studentId) {
                // Tìm sinh viên theo ID
                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + studentId));

                // Lấy danh sách các lớp học mà sinh viên đang tham gia
                List<CourseClass> courseClasses = student.getListOfCourseClasses();

                for (CourseClass courseClass : courseClasses) {

                        // Xóa sinh viên khỏi danh sách sinh viên trong lớp học
                        List<Student> studentsInClass = courseClass.getListOfStudents();
                        studentsInClass.remove(student);

                        // Cập nhật lớp học
                        courseClass.setListOfStudents(studentsInClass);

                        // Lưu lớp học đã cập nhật
                        courseClassRepository.save(courseClass);
                }

                // Xóa sinh viên khỏi danh sách lớp học của sinh viên
                student.setListOfCourseClasses(new ArrayList<>());
                studentRepository.save(student);
        }

        // Xóa sinh viên ra khỏi một lớp và đồng thời xóa bảng điểm của sinh viên trong
        // lớp đó
        // Xóa sinh viên ra khỏi một lớp và đồng thời xóa bảng điểm của sinh viên trong
        // lớp đó
        public void removeStudentFromClass(RemoveStudentRequest request) {
                // Tìm sinh viên theo Id trong request
                Student student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Student not found with ID: " + request.getStudentId()));

                // Tìm lớp học dựa trên thông tin trong request
                CourseClass courseClass = courseClassRepository.findById_CourseCodeAndId_SemesterCodeAndId_ClassName(
                                request.getCourseCode(), request.getSemesterCode(), request.getClassName())
                                .orElseThrow(() -> new IllegalArgumentException("Course class not found"));

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
        }

}
