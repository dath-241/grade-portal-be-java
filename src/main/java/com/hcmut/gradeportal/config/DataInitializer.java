package com.hcmut.gradeportal.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.semester.request.CreateSemesterRequest;
import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.entities.Course;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.entities.SheetMark;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.ClassStatus;
import com.hcmut.gradeportal.helper.dataLoader.AdminDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.CourseDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.SemesterDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.StudentDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.TeacherDataLoader;
import com.hcmut.gradeportal.repositories.CourseRepository;
import com.hcmut.gradeportal.repositories.SheetMarkRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.service.AdminService;
import com.hcmut.gradeportal.service.CourseClassService;
import com.hcmut.gradeportal.service.CourseService;
import com.hcmut.gradeportal.service.SemesterService;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final SheetMarkRepository sheetMarkRepository;

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

    public DataInitializer(StudentRepository studentRepository, TeacherRepository teacherRepository,
            CourseRepository courseRepository, SheetMarkRepository sheetMarkRepository, StudentService studentService,
            TeacherService teacherService, AdminService adminService, CourseService courseService,
            SemesterService semesterService, CourseClassService courseClassService, StudentDataLoader studentDataLoader,
            TeacherDataLoader teacherDataLoader, AdminDataLoader adminDataLoader, CourseDataLoader courseDataLoader,
            SemesterDataLoader semesterDataLoader) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.sheetMarkRepository = sheetMarkRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
        this.courseService = courseService;
        this.semesterService = semesterService;
        this.courseClassService = courseClassService;
        this.studentDataLoader = studentDataLoader;
        this.teacherDataLoader = teacherDataLoader;
        this.adminDataLoader = adminDataLoader;
        this.courseDataLoader = courseDataLoader;
        this.semesterDataLoader = semesterDataLoader;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Starting data initialization...");

        try {
            initializeBaseData(); // Gồm Admin, Teacher, Student, Semester, Course
        } catch (Exception e) {
            System.err.println("Failed to initialize base data: " + e.getMessage());
            return;
        }

        // try {
        // initializeCourseClassesAndSheetMarks();
        // } catch (Exception e) {
        // System.err.println("Failed to initialize course classes or sheet marks: " +
        // e.getMessage());
        // }

        System.out.println("Data initialization completed.");

    }

    @Transactional
    public void initializeBaseData() {
        initializeAdmins();
        initializeTeachers();
        initializeStudents();
        initializeSemesters();
        initializeCourses();
    }

    @Transactional
    public void initializeAdmins() {
        System.out.println("Initializing Admins...");
        try {
            Set<CreateAdminRequest> admins = adminDataLoader.loadAdminData();
            admins.forEach(adminDto -> {
                try {
                    adminService.createAdmin(adminDto);
                    System.out.println(
                            "Successfully seeded admin: " + adminDto.getFamilyName() + " " + adminDto.getGivenName());
                } catch (Exception e) {
                    System.err.println(
                            "Failed to seed admin " + adminDto.getFamilyName() + " " + adminDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load admin data from JSON: " + e.getMessage());
        }
    }

    @Transactional
    public void initializeTeachers() {
        System.out.println("Initializing Teachers...");
        try {
            Set<CreateTeacherRequest> teachers = teacherDataLoader.loadTeacherData();
            teachers.forEach(teacherDto -> {
                try {
                    teacherService.createTeacher(teacherDto);
                    System.out.println("Successfully seeded teacher: " + teacherDto.getFamilyName() + " "
                            + teacherDto.getGivenName());
                } catch (Exception e) {
                    System.err.println(
                            "Failed to seed teacher " + teacherDto.getFamilyName() + " " + teacherDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load teacher data from JSON: " + e.getMessage());
        }
    }

    @Transactional
    public void initializeStudents() {
        System.out.println("Initializing Students...");
        try {
            Set<CreateStudentRequest> students = studentDataLoader.loadStudentData();
            students.forEach(studentDto -> {
                try {
                    studentService.createStudent(studentDto);
                    System.out.println("Successfully seeded student: " + studentDto.getFamilyName() + " "
                            + studentDto.getGivenName());
                } catch (Exception e) {
                    System.err.println(
                            "Failed to seed student " + studentDto.getFamilyName() + " " + studentDto.getGivenName());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load student data from JSON: " + e.getMessage());
        }
    }

    @Transactional
    public void initializeSemesters() {
        System.out.println("Initializing Semesters...");
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
    }

    @Transactional
    public void initializeCourses() {
        System.out.println("Initializing Courses...");
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
    }

    @Transactional
    public void initializeCourseClassesAndSheetMarks() {
        System.out.println("Initializing CourseClasses and SheetMarks...");
        try {
            List<Course> courses = courseRepository.findAll();
            Collections.shuffle(courses);
            List<Course> selectedCourses = courses.stream().limit(10).collect(Collectors.toList());

            List<Student> students = studentRepository.findAll();
            Collections.shuffle(students);
            int groupSize = 40;
            List<Student> group1 = students.subList(0, groupSize);
            List<Student> group2 = students.subList(groupSize, groupSize * 2);

            List<Teacher> teachers = teacherRepository.findAll();

            for (Course course : selectedCourses) {
                for (int j = 0; j < 2; j++) {
                    String className = "L0" + (j + 1);
                    Teacher randomTeacher = teachers.get(new Random().nextInt(teachers.size()));
                    List<Student> assignedStudents = (j == 0) ? group1 : group2;

                    List<String> studentIds = assignedStudents.stream().map(Student::getId)
                            .collect(Collectors.toList());
                    CreateCourseClassRequest createRequest = new CreateCourseClassRequest(
                            course.getCourseCode(), "HK241", className, randomTeacher.getId(), studentIds,
                            ClassStatus.inProgress);

                    CourseClass courseClass = courseClassService.createCourseClass(createRequest);

                    for (Student student : assignedStudents) {
                        SheetMark sheetMark = sheetMarkRepository
                                .findByStudentIdAndCourseCodeAndSemesterCodeAndClassName(
                                        student.getId(), courseClass.getId().getCourseCode(),
                                        courseClass.getId().getSemesterCode(), courseClass.getId().getClassName())
                                .orElseThrow(() -> new RuntimeException("SheetMark not found"));

                        sheetMark.setBT(Arrays.asList(Math.random() * 10, Math.random() * 10));
                        sheetMark.setTN(Arrays.asList(Math.random() * 10, Math.random() * 10));
                        sheetMark.setBTL(Arrays.asList(Math.random() * 10));
                        sheetMark.setGK(Math.random() * 10);
                        sheetMark.setCK(Math.random() * 10);
                        sheetMark.updateFinalMark();

                        sheetMarkRepository.save(sheetMark);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to seed course classes or sheet marks: " + e.getMessage());
        }
    }

}
