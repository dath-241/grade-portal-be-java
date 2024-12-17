package com.hcmut.gradeportal.controller.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
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
