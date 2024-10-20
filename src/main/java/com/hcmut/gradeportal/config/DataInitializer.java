package com.hcmut.gradeportal.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.helper.dataLoader.AdminDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.StudentDataLoader;
import com.hcmut.gradeportal.helper.dataLoader.TeacherDataLoader;
import com.hcmut.gradeportal.service.AdminService;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

@Component
public class DataInitializer implements CommandLineRunner {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdminService adminService;

    private final StudentDataLoader studentDataLoader;
    private final TeacherDataLoader teacherDataLoader;
    private final AdminDataLoader adminDataLoader;


    public DataInitializer(  StudentService studentService, 
                            TeacherService teacherService, 
                            AdminService adminService,
                            StudentDataLoader studentDataLoader,
                            AdminDataLoader adminDataLoader, 
                            TeacherDataLoader teacherDataLoader) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;

        this.adminDataLoader = adminDataLoader;
        this.teacherDataLoader = teacherDataLoader;
        this.studentDataLoader = studentDataLoader;
    }

    // Sử dụng @Value để đọc biến môi trường hoặc đối số dòng lệnh
    @Value("${init-data:false}")  // Mặc định là false nếu không được chỉ định
    private boolean initData;

    @Override
    public void run(String... args) throws Exception {
        if (initData) {
            // Chỉ khởi tạo dữ liệu nếu biến init-data được kích hoạt
            System.out.println("Starting data initialization...");
            
            try {
                Set<CreateStudentRequest> students = studentDataLoader.loadStudentData();
                students.forEach(studentDto -> {
                    try {
                        studentService.createStudent(studentDto);
                        System.out.println("Successfully seeded student: " + studentDto.getFamilyName() + " " + studentDto.getGivenName());
                    } catch (Exception e) {
                        System.err.println("Failed to seed student " + studentDto.getFamilyName() + " " + studentDto.getGivenName());
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
                        System.out.println("Successfully seeded teacher: " + teacherDto.getFamilyName() + " " + teacherDto.getGivenName());
                    } catch (Exception e) {
                        System.err.println("Failed to seed teacher " + teacherDto.getFamilyName() + " " + teacherDto.getGivenName());
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
                        System.out.println("Successfully seeded admin: " + adminDto.getFamilyName() + " " + adminDto.getGivenName());
                    } catch (Exception e) {
                        System.err.println("Failed to seed admin " + adminDto.getFamilyName() + " " + adminDto.getGivenName());
                    }
                });
            } catch (IOException e) {
                System.err.println("Failed to load admin data from JSON: " + e.getMessage());
            }


        } else {
            System.out.println("Data initialization is skipped.");
        }
    }
}
