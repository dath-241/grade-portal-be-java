package com.hcmut.gradeportal.controller.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.student.StudentDto;
import com.hcmut.gradeportal.dtos.student.StudentDtoConverter;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
import com.hcmut.gradeportal.service.StudentService;

@RestController
@RequestMapping("/student/auth")
public class StudentAuthController {
    private final StudentDtoConverter studentDtoConverter;
    private final StudentService studentService;

    public StudentAuthController(StudentService studentService, StudentDtoConverter studentDtoConverter) {
        this.studentService = studentService;
        this.studentDtoConverter = studentDtoConverter;
    }

    ///////////////// All Get request for student auth /////////////////
    // Get Personal Information
    @GetMapping("/get-info")
    public ResponseEntity<ApiResponse<StudentDto>> getPersonalInfo() {
        try {
            String userId = CurrentUser.getUserId();

            StudentDto StudentDto = studentDtoConverter.convert(studentService.getStudentById(userId));
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Get personal information",
                    StudentDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Post request for student auth /////////////////

    ///////////////// All Put request for student auth /////////////////

    ///////////////// All Delete request for student auth /////////////////
}
