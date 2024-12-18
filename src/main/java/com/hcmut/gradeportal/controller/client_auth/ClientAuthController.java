package com.hcmut.gradeportal.controller.client_auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.auth.request.AuthRequestForUser;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.dtos.student.StudentDto;
import com.hcmut.gradeportal.dtos.student.StudentDtoConverter;
import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.enums.Role;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.GoogleTokenVerifierService;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

@RestController
@RequestMapping("/auth")
public class ClientAuthController {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    private final StudentService studentService;
    private final TeacherService teacherService;

    private final StudentDtoConverter studentDtoConverter;
    private final TeacherDtoConverter teacherDtoConverter;

    private final GoogleTokenVerifierService googleTokenVerifierService;

    public ClientAuthController(StudentRepository studentRepository, TeacherRepository teacherRepository,
            GoogleTokenVerifierService googleTokenVerifierService, StudentService studentService,
            TeacherService teacherService, StudentDtoConverter studentDtoConverter,
            TeacherDtoConverter teacherDtoConverter) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;

        this.studentService = studentService;
        this.teacherService = teacherService;

        this.studentDtoConverter = studentDtoConverter;
        this.teacherDtoConverter = teacherDtoConverter;

        this.googleTokenVerifierService = googleTokenVerifierService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse<?>>> login(@RequestBody AuthRequestForUser request) {
        try {
            // Xác thực Google ID Token
            var payload = googleTokenVerifierService.verify(request.getIdToken());

            String email = payload.getEmail();

            // Kiểm tra loại người dùng: Student hoặc Teacher
            Optional<Student> student = studentRepository.findByEmailAndRole(email, Role.STUDENT);
            Optional<Teacher> teacher = teacherRepository.findByEmailAndRole(email, Role.TEACHER);

            if (student.isPresent()) {
                // Xử lý đăng nhập cho Student
                AuthResponse<Student> authResponse = studentService.login(request.getIdToken());

                AuthResponse<StudentDto> response = new AuthResponse<>(
                        authResponse.getToken(),
                        studentDtoConverter.convert(authResponse.getUser()),
                        authResponse.getRole());
                return ResponseEntity.ok(
                        new ApiResponse<>(HttpStatus.OK.value(), "Login success", response));
            } else if (teacher.isPresent()) {
                // Xử lý đăng nhập cho Teacher
                AuthResponse<Teacher> authResponse = teacherService.login(request.getIdToken());

                AuthResponse<TeacherDto> response = new AuthResponse<>(
                        authResponse.getToken(),
                        teacherDtoConverter.convert(authResponse.getUser()),
                        authResponse.getRole());
                return ResponseEntity.ok(
                        new ApiResponse<>(HttpStatus.OK.value(), "Login success", response));
            } else {
                // Trường hợp không tìm thấy người dùng
                throw new IllegalArgumentException("User not found");
            }

        } catch (IllegalArgumentException e) {
            // Lỗi do yêu cầu không hợp lệ
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            // Lỗi hệ thống hoặc các lỗi không mong muốn
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }
}
