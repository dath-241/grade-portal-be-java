package com.hcmut.gradeportal.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.hcmut.gradeportal.dtos.course.CourseDto;
import com.hcmut.gradeportal.dtos.course.CourseDtoConverter;
import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;
import com.hcmut.gradeportal.dtos.course.request.GetCourseRequest;
import com.hcmut.gradeportal.dtos.course.response.CreateBulkCourseResponse;
///import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequest;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseService;

@RestController
@RequestMapping("/admin/manage-course")
public class AdminManageCourseController {
    private final CourseDtoConverter courseDtoConverter;
    private final CourseService courseService;

    public AdminManageCourseController(CourseService courseService, CourseDtoConverter courseDtoConverter) {
        this.courseService = courseService;
        this.courseDtoConverter = courseDtoConverter;
    }

    ///////////////// All Get request for manage course /////////////////
    // Get all courses
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        try {
            List<CourseDto> courseDtos = courseDtoConverter.convert(courseService.getAllCourses());
            ApiResponse<List<CourseDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get all courses",
                    courseDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CourseDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get course by course code
    @GetMapping("/get-by-course-code/{courseCode}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseByCourseCode(@PathVariable String courseCode) {
        try {
            CourseDto courseDto = courseDtoConverter.convert(courseService.getCourseByCourseCode(courseCode));
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Get course by course code",
                    courseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Post request for manage course /////////////////
    // Create a new course
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody CreateCourseRequest request) {
        try {
            CourseDto courseDto = courseDtoConverter.convert(courseService.createCourse(request));
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Create course successfully",
                    courseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a list of courses
    @PostMapping("/create-bulk")
    public ResponseEntity<ApiResponse<List<CreateBulkCourseResponse>>> createBulkCourses(
            @RequestBody List<CreateCourseRequest> requests) {
        try {
            List<CreateBulkCourseResponse> responses = new ArrayList<>();
            for (CreateCourseRequest request : requests) {
                try {
                    CourseDto courseDto = courseDtoConverter.convert(courseService.createCourse(request));
                    responses.add(new CreateBulkCourseResponse(courseDto.courseCode(), HttpStatus.OK.value(),
                            "Create course successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkCourseResponse(request.getCourseCode(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkCourseResponse(request.getCourseCode(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
                }
            }

            ApiResponse<List<CreateBulkCourseResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Create courses", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkCourseResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create courses", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    ///////////////// All Put request for manage course /////////////////
    // Update course details
    @PostMapping("/update-course")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(@RequestBody CreateCourseRequest request) {
        try {
            CourseDto courseDto = courseDtoConverter.convert(courseService.updateCourse(request));
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Update course successfully",
                    courseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Update course failed", 
                    null);      
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Delete request for manage course /////////////////
    // Delete a course using request body
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@RequestBody GetCourseRequest request) {
        try {
            // Directly pass the CreateCourseRequest object to the service method
            courseService.deleteCourse(request);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "Delete course successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Deleted course failed", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}