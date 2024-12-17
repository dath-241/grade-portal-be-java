package com.hcmut.gradeportal.controller.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.courseClass.CourseClassDetailDtoForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDetailDtoForTeacherConverter;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.CourseClassDtoForTeacherConverter;
import com.hcmut.gradeportal.dtos.courseClass.request.AddStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.GetCourseClassRequestForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.request.GetDetailCourseClassRequestForTeacher;
import com.hcmut.gradeportal.dtos.courseClass.request.RemoveStudentRequest;
import com.hcmut.gradeportal.dtos.courseClass.request.UpdateClassStatusRequest;
import com.hcmut.gradeportal.entities.CourseClass;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
import com.hcmut.gradeportal.service.CourseClassService;

@RestController
@RequestMapping("/teacher/manage-class")
public class TeacherManageClassController {
    private final CourseClassService courseClassService;
    private final CourseClassDtoForTeacherConverter courseClassDtoForTeacherConverter;
    private final CourseClassDetailDtoForTeacherConverter courseClassDetailDtoForTeacherConverter;

    public TeacherManageClassController(CourseClassService courseClassService,
            CourseClassDtoForTeacherConverter courseClassDtoForTeacherConverter,
            CourseClassDetailDtoForTeacherConverter courseClassDetailDtoForTeacherConverter) {
        this.courseClassService = courseClassService;
        this.courseClassDtoForTeacherConverter = courseClassDtoForTeacherConverter;
        this.courseClassDetailDtoForTeacherConverter = courseClassDetailDtoForTeacherConverter;
    }

    // Xem các lớp đã và đang giảng dạy
    // bằng teacher ID
    @GetMapping("/get-class-by-teacher")
    public ResponseEntity<ApiResponse<List<CourseClassDtoForTeacher>>> getClassByTeacherId() {
        try {
            String userId = CurrentUser.getUserId();
            System.out.println("userId: " + userId);

            List<CourseClassDtoForTeacher> courseClassDtos = courseClassDtoForTeacherConverter
                    .convert(courseClassService.getClassByTeacherId(userId));
            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Phản hồi lỗi
            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // // Bằng user ID
    // @GetMapping("/get-class-by-teacher-user-id/{UserId}")
    // public ResponseEntity<ApiResponse<List<CourseClassDto>>>
    // getClassByTeacherUserId(@PathVariable String UserId) {
    // try {
    // List<CourseClassDto> courseClassDtos = courseClassDtoConverter
    // .convert(courseClassService.getClassByTeacherUserId(UserId));
    // ApiResponse<List<CourseClassDto>> response = new
    // ApiResponse<>(HttpStatus.OK.value(), "Get classes",
    // courseClassDtos);

    // return new ResponseEntity<>(response, HttpStatus.OK);
    // } catch (IllegalStateException | IllegalArgumentException e) {

    // ApiResponse<List<CourseClassDto>> response = new
    // ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
    // e.getMessage(), null);
    // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    // } catch (Exception e) {
    // // Phản hồi lỗi
    // ApiResponse<List<CourseClassDto>> response = new
    // ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    // "Failed to fetch classes", null);
    // return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    // get class by specification
    @GetMapping("/get-class-by-specification")
    public ResponseEntity<ApiResponse<List<CourseClassDtoForTeacher>>> getClassBySpecification(
            @RequestBody GetCourseClassRequestForTeacher request) {
        try {
            String teacherId = CurrentUser.getUserId();

            List<CourseClass> classes = courseClassService.teacherGetClassBySpecification(teacherId, request);
            List<CourseClassDtoForTeacher> courseClassDtos = courseClassDtoForTeacherConverter.convert(classes);
            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get class by specification",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<CourseClassDtoForTeacher>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get student info by class
    @GetMapping("/get-detail")
    public ResponseEntity<ApiResponse<CourseClassDetailDtoForTeacher>> getStudentInfoByClass(
            @RequestBody GetDetailCourseClassRequestForTeacher request) {
        try {
            String userId = CurrentUser.getUserId();

            CourseClass courseClass = courseClassService.getDetailCourseClass(request, userId);
            CourseClassDetailDtoForTeacher courseClassDetailDto = courseClassDetailDtoForTeacherConverter
                    .convert(courseClass);

            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Get detail",
                    courseClassDetailDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Thêm sinh viên vào lớp học
    @PutMapping("/add-student")
    public ResponseEntity<ApiResponse<CourseClassDetailDtoForTeacher>> addStudentToClass(
            @RequestBody AddStudentRequest request) {
        try {
            String userId = CurrentUser.getUserId();

            // Thêm sinh viên vào lớp học
            CourseClass courseClass = courseClassService.addStudentToClass(request, userId);
            CourseClassDetailDtoForTeacher courseClassDetailDto = courseClassDetailDtoForTeacherConverter
                    .convert(courseClass);

            // Phản hồi thành công
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Student added successfully", courseClassDetailDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật trạng thái của lớp học
    @PutMapping("/update-class-status")
    public ResponseEntity<ApiResponse<CourseClassDetailDtoForTeacher>> UpdateClassStatus(
            @RequestBody UpdateClassStatusRequest request) {
        try {
            String userId = CurrentUser.getUserId();

            CourseClassDetailDtoForTeacher courseClassDto = courseClassDetailDtoForTeacherConverter
                    .convert(courseClassService.updateStatusCourseClassForTeacher(request, userId));
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Update class status",
                    courseClassDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xóa sinh viên khỏi lớp học
    @DeleteMapping("/remove-student")
    public ResponseEntity<ApiResponse<CourseClassDetailDtoForTeacher>> removeStudentFromClass(
            @RequestBody RemoveStudentRequest request) {
        try {
            String userId = CurrentUser.getUserId();

            // Xóa sinh viên khỏi lớp học
            CourseClass courseClass = courseClassService.removeStudentFromClass(request, userId);
            CourseClassDetailDtoForTeacher courseClassDetailDto = courseClassDetailDtoForTeacherConverter
                    .convert(courseClass);
            // Phản hồi thành công
            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Student removed successfully", courseClassDetailDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            ApiResponse<CourseClassDetailDtoForTeacher> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to remove student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
