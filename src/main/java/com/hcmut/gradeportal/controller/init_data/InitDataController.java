package com.hcmut.gradeportal.controller.init_data;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.semester.request.CreateSemesterRequest;
import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.helper.dataLoader.AdminDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.CourseClassDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.CourseDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.SemesterDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.StudentDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.TeacherDataLoader;
import com.hcmut.gradeportal.service.AdminService;
import com.hcmut.gradeportal.service.CourseClassService;
import com.hcmut.gradeportal.service.CourseService;
import com.hcmut.gradeportal.service.SemesterService;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.hcmut.gradeportal.response.ApiResponse;

@RestController
@RequestMapping("/init-data")
public class InitDataController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdminService adminService;
    private final CourseService courseService;
    private final SemesterService semesterService;
    private final CourseClassService courseClassService;

    private final StudentDataLoader studentDataLoader;
    private final TeacherDataLoader teacherDataLoader;
    private final AdminDataLoader adminDataLoader;
    private final CourseDataLoader courseDataLoader;
    private final SemesterDataLoader semesterDataLoader;
    private final CourseClassDataLoader courseClassDataLoader;

    public InitDataController(StudentService studentService,
            TeacherService teacherService,
            AdminService adminService,
            CourseService courseService,
            SemesterService semesterService,
            CourseClassService courseClassService,
            StudentDataLoader studentDataLoader,
            AdminDataLoader adminDataLoader,
            TeacherDataLoader teacherDataLoader,
            CourseDataLoader courseDataLoader,
            SemesterDataLoader semesterDataLoader,
            CourseClassDataLoader courseClassDataLoader) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
        this.courseService = courseService;
        this.semesterService = semesterService;
        this.courseClassService = courseClassService;

        this.adminDataLoader = adminDataLoader;
        this.teacherDataLoader = teacherDataLoader;
        this.studentDataLoader = studentDataLoader;
        this.courseDataLoader = courseDataLoader;
        this.semesterDataLoader = semesterDataLoader;
        this.courseClassDataLoader = courseClassDataLoader;
    }

    // Init data
    // Chỉ khởi tạo dữ liệu nếu biến init-data được kích hoạt
    @GetMapping()
    public ResponseEntity<ApiResponse<String>> initData() {
        System.out.println("Starting data initialization...");

        try

        {
            Set<CreateStudentRequest> students = studentDataLoader.loadStudentData();
            students.forEach(studentDto -> {
                try {
                    studentService.createStudent(studentDto);
                    System.out.println("Successfully seeded student: " + studentDto.getFamilyName() + " "
                            + studentDto.getGivenName());
                } catch (Exception e) {
                    System.err.println("Failed to seed student " + studentDto.getFamilyName() + " "
                            + studentDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load student data from JSON: " + e.getMessage());
        }

        try {
            Set<CreateTeacherRequest> teachers = teacherDataLoader.loadTeacherData();
            teachers.forEach(teacherDto -> {
                try {
                    teacherService.createTeacher(teacherDto);
                    System.out.println("Successfully seeded teacher: " + teacherDto.getFamilyName() + " "
                            + teacherDto.getGivenName());
                } catch (Exception e) {
                    System.err.println("Failed to seed teacher " + teacherDto.getFamilyName() + " "
                            + teacherDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load teacher data from JSON: " + e.getMessage());
        }

        try {
            Set<CreateAdminRequest> admins = adminDataLoader.loadAdminData();
            admins.forEach(adminDto -> {
                try {
                    adminService.createAdmin(adminDto);
                    System.out.println("Successfully seeded admin: " + adminDto.getFamilyName() + " "
                            + adminDto.getGivenName());
                } catch (Exception e) {
                    System.err.println(
                            "Failed to seed admin " + adminDto.getFamilyName() + " " + adminDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load admin data from JSON: " + e.getMessage());
        }

        try {
            Set<CreateCourseRequest> courses = courseDataLoader.loadCourseData();
            courses.forEach(courseDto -> {
                try {
                    courseService.createCourse(courseDto);
                    System.out.println("Successfully seeded course: " + courseDto.getCourseName());
                } catch (Exception e) {
                    System.err.println("Failed to seed course " + courseDto.getCourseName());
                }
            });
        } catch (UnsupportedOperationException e) {
            System.err.println("Failed to load course data from JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to load course data from JSON: " + e.getMessage());
        }

        try {
            Set<CreateSemesterRequest> semesters = semesterDataLoader.loadSemesterData();
            semesters.forEach(semesterDto -> {
                try {
                    semesterService.createSemester(semesterDto);
                    System.out.println("Successfully seeded semester: " + semesterDto.getSemesterName());
                } catch (Exception e) {
                    System.err.println("Failed to seed semester " + semesterDto.getSemesterName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load semester data from JSON: " + e.getMessage());
        }

        try {
            Set<CreateCourseClassRequest> courseClasses = courseClassDataLoader.loadCourseClassData();
            courseClasses.forEach(courseClassDto -> {
                try {
                    courseClassService.createCourseClassWhenInit(courseClassDto);
                    System.out.println("Successfully seeded course class: " + courseClassDto.getSemesterCode() + " "
                            + courseClassDto.getCourseCode() + " " + courseClassDto.getClassName());
                } catch (Exception e) {
                    System.err.println("Failed to seed course class " + courseClassDto.getSemesterCode() + " "
                            + courseClassDto.getCourseCode() + " " + courseClassDto.getClassName());
                }
            });

            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.SC_OK, "Data initialization completed", null));

        } catch (IOException e) {
            System.err.println("Failed to load course class data from JSON: " + e.getMessage());
            return ResponseEntity
                    .ok(new ApiResponse<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Data initialization failed",
                            null));
        }
    }

}
