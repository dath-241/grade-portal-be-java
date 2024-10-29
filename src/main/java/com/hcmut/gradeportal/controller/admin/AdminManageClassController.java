package com.hcmut.gradeportal.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDto;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoConverter;
import com.hcmut.gradeportal.dtos.courseClass.request.ChangeTeacherRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.CreateCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateStudentsInClassRequest;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseClassService;

@RestController
@RequestMapping("/admin/manage-class")
public class AdminManageClassController {
    private final CourseClassService courseClassService;
    private final CourseClassDtoConverter courseClassDtoConverter;

    public AdminManageClassController(CourseClassService courseClassService,
            CourseClassDtoConverter courseClassDtoConverter) {
        this.courseClassService = courseClassService;
        this.courseClassDtoConverter = courseClassDtoConverter;
    }

    /////////////////////// All Get request for manage class ///////////////////////
    // Get all classes
    @GetMapping("/get-all-classes")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getAllClasses() {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getAllCourseClasses());
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get all classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get course class by specification
    @GetMapping("/get-class-by-specification")
    public ResponseEntity<ApiResponse<List<CourseClassDto>>> getClassBySpecification(
            @RequestBody GetCourseClassRequest request) {
        try {
            List<CourseClassDto> courseClassDtos = courseClassDtoConverter
                    .convert(courseClassService.getCourseClassesBySpecification(request));
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get class by specification",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<CourseClassDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////// All Post request for manage class
    /////////////////////// ///////////////////////
    // Create a new course class
    @PostMapping("/create-class")
    public ResponseEntity<ApiResponse<CourseClassDto>> createClass(@RequestBody CreateCourseClassRequest request) {
        try {
            CourseClassDto courseClassDto = courseClassDtoConverter
                    .convert(courseClassService.createCourseClass(request));
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Create class successfully",
                    courseClassDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////// All Put request for manage class ///////////////////////
    // Change or assign teacher to class
    @PutMapping("/teacher-to-class")
    public ResponseEntity<ApiResponse<CourseClassDto>> teacherToClass(@RequestBody ChangeTeacherRequest request) {
        try {
            CourseClassDto courseClassDto = courseClassDtoConverter
                    .convert(courseClassService.changeTeacherOfCourseClass(request));
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Change teacher to class",
                    courseClassDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update student list of class
    @PutMapping("/update-students-class")
    public ResponseEntity<ApiResponse<CourseClassDto>> updateStudentsClass(
            @RequestBody UpdateStudentsInClassRequest request) {
        try {
            CourseClassDto courseClassDto = courseClassDtoConverter
                    .convert(courseClassService.updateStudentsOfCourseClass(request));
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Update students in class",
                    courseClassDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseClassDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////////////// All Delete request for manage class
    /////////////////////// ///////////////////////

}
