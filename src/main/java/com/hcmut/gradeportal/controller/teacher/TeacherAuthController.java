package com.hcmut.gradeportal.controller.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.auth.request.AuthRequestForUser;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
import com.hcmut.gradeportal.service.TeacherService;

@RestController
@RequestMapping("/teacher/auth")
public class TeacherAuthController {
    private final TeacherDtoConverter teacherDtoConverter;
    private final TeacherService teacherService;

    public TeacherAuthController(TeacherDtoConverter teacherDtoConverter, TeacherService teacherService) {
        this.teacherDtoConverter = teacherDtoConverter;
        this.teacherService = teacherService;
    }

    //////////////// Login Method ////////////////
    /// Login Method for teacher
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse<?>>> login(@RequestBody AuthRequestForUser request) {
        try {

            // Xử lý đăng nhập cho Teacher
            AuthResponse<Teacher> authResponse = teacherService.login(request.getIdToken());

            AuthResponse<TeacherDto> response = new AuthResponse<>(
                    authResponse.getToken(),
                    teacherDtoConverter.convert(authResponse.getUser()),
                    authResponse.getRole());
            return ResponseEntity.ok(
                    new ApiResponse<>(HttpStatus.OK.value(), "Login success", response));

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

    ///////////////// All Get request for teacher auth /////////////////
    // Get Personal Information
    @GetMapping("/get-info")
    public ResponseEntity<ApiResponse<TeacherDto>> getPersonalInfo() {
        try {
            String userId = CurrentUser.getUserId();

            TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.getTeacherById(userId));
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Get personal information",
                    teacherDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Post request for teacher auth /////////////////

    ///////////////// All Put request for teacher auth /////////////////

    ///////////////// All Delete request for teacher auth /////////////////
}
