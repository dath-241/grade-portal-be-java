package com.hcmut.gradeportal.controller.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoForStudent;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoForStudentConverter;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequestForStudent;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
import com.hcmut.gradeportal.service.CourseClassService;

@RestController
@RequestMapping("/student/class-info")
public class StudentClassInfoController {

    private final CourseClassService courseClassService;
    private final CourseClassDtoForStudentConverter courseClassDtoForStudentConverter;

    public StudentClassInfoController(CourseClassService courseClassService,
            CourseClassDtoForStudentConverter courseClassDtoForStudentConverter) {
        this.courseClassService = courseClassService;
        this.courseClassDtoForStudentConverter = courseClassDtoForStudentConverter;
    }

    ///////////////// All Get request for student class info /////////////////

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<CourseClassDtoForStudent>>> getClassByStudent() {
        try {
            String userId = CurrentUser.getUserId();

            List<CourseClassDtoForStudent> courseClassDtos = courseClassDtoForStudentConverter
                    .convert(courseClassService.getClassByStudentId(userId));
            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get class by specification
    @GetMapping("/get-by-specification")
    public ResponseEntity<ApiResponse<List<CourseClassDtoForStudent>>> getClassBySpecification(
            @RequestBody GetCourseClassRequestForStudent request) {
        try {
            String studentId = CurrentUser.getUserId();

            List<CourseClass> classes = courseClassService.studentGetClassBySpecification(studentId, request);
            List<CourseClassDtoForStudent> courseClassDtos = courseClassDtoForStudentConverter.convert(classes);
            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get class by specification",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<CourseClassDtoForStudent>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
