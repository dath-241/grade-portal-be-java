package com.hcmut.gradeportal.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.student.StudentDto;
import com.hcmut.gradeportal.dtos.student.StudentDtoConverter;
import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.student.request.GetStudentRequest;
import com.hcmut.gradeportal.dtos.student.request.UpdateStudentRequest;
import com.hcmut.gradeportal.dtos.student.response.CreateBulkStudentResponse;
import com.hcmut.gradeportal.dtos.student.response.UpdateBulkStudentResponse;
import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.GetTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.UpdateTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.response.CreateBulkTeacherResponse;
import com.hcmut.gradeportal.dtos.teacher.response.UpdateBulkTeacherResponse;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.CourseClassService;
import com.hcmut.gradeportal.service.SheetMarkService;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

@RestController
@RequestMapping("/admin/manage-user")
public class AdminManageUserController {
    private final TeacherDtoConverter teacherDtoConverter;
    private final StudentDtoConverter studentDtoConverter;

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SheetMarkService sheetMarkService;
    private final CourseClassService courseClassService;

    public AdminManageUserController(
            TeacherService teacherService, StudentService studentService,
            SheetMarkService sheetMarkService, CourseClassService courseClassService,
            TeacherDtoConverter teacherDtoConverter, StudentDtoConverter studentDtoConverter) {

        this.teacherDtoConverter = teacherDtoConverter;
        this.studentDtoConverter = studentDtoConverter;

        this.teacherService = teacherService;
        this.studentService = studentService;
        this.sheetMarkService = sheetMarkService;
        this.courseClassService = courseClassService;

    }

    /////////////// All get request for manage account - Teacher ///////////////
    // Get all teachers
    @GetMapping("/get-all-teachers")
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getAllTeachers() {
        try {
            List<TeacherDto> teacherDtos = teacherDtoConverter.convert(teacherService.getAllTeachers()); // Chuyển sang
                                                                                                         // DTO

            ApiResponse<List<TeacherDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Teachers retrieved",
                    teacherDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<TeacherDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve teachers", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a teacher by id of user
    @GetMapping("/get-teacher-by-id/{userId}")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(@PathVariable String userId) {
        try {
            TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.getTeacherById(userId)); // Chuyển sang
            // DTO

            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Teacher retrieved",
                    teacherDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve teacher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a teacher by id of teacher
    @GetMapping("/get-teacher-by-teacher-id/{teacherId}")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherByTeacherId(@PathVariable String teacherId) {
        try {
            TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.getTeacherByTeacherId(teacherId)); // Chuyển
                                                                                                                  // sang
                                                                                                                  // DTO

            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Teacher retrieved",
                    teacherDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve teacher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get teachers by specification
    @GetMapping("/get-teachers-by-specification")
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getTeachersBySpecification(
            @RequestBody GetTeacherRequest request) {
        try {
            List<TeacherDto> teacherDtos = teacherDtoConverter
                    .convert(teacherService.getTeachersBySpecification(request)); // Chuyển sang DTO

            ApiResponse<List<TeacherDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Teachers retrieved",
                    teacherDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<TeacherDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve teachers", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //

    /////////////// All get request for manage account - Student ///////////////
    // Get all students
    @GetMapping("/get-all-students")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {
        try {
            List<StudentDto> studentDtos = studentDtoConverter.convert(studentService.getAllStudents()); // Chuyển sang
                                                                                                         // DTO

            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Students retrieved",
                    studentDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve students", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a student by id of user
    @GetMapping("/get-student-by-id/{userId}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable String userId) {
        try {
            StudentDto studentDto = studentDtoConverter.convert(studentService.getStudentById(userId)); // Chuyển sang
            // DTO

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student retrieved",
                    studentDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a student by id of student
    @GetMapping("/get-student-by-student-id/{studentId}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentByStudentId(@PathVariable String studentId) {
        try {
            StudentDto studentDto = studentDtoConverter.convert(studentService.getStudentByStudentId(studentId)); // Chuyển
                                                                                                                  // sang
                                                                                                                  // DTO

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student retrieved",
                    studentDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get students by specification
    @GetMapping("/get-students-by-specification")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentsBySpecification(
            @RequestBody GetStudentRequest request) {
        try {
            List<StudentDto> studentDtos = studentDtoConverter
                    .convert(studentService.getStudentsBySpecification(request)); // Chuyển sang DTO

            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Students retrieved",
                    studentDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<StudentDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve students", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All post request for manage account - Teacher ///////////////
    // Create a new teacher
    @PostMapping("/create-teacher")
    public ResponseEntity<ApiResponse<TeacherDto>> createTeacher(@RequestBody CreateTeacherRequest request) {
        try {
            TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.createTeacher(request)); // Chuyển sang
                                                                                                        // DTO

            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Teacher created successfully",
                    teacherDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create teacher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a list of teachers
    @PostMapping("/create-bulk-teachers")
    public ResponseEntity<ApiResponse<List<CreateBulkTeacherResponse>>> createBulkTeachers(
            @RequestBody List<CreateTeacherRequest> requests) {
        try {
            List<CreateBulkTeacherResponse> responses = new ArrayList<>();

            for (CreateTeacherRequest request : requests) {
                try {
                    TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.createTeacher(request)); // Chuyển
                                                                                                                // sang
                                                                                                                // DTO
                    responses.add(new CreateBulkTeacherResponse(teacherDto.email(), HttpStatus.OK.value(),
                            "Teacher created successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkTeacherResponse(request.getEmail(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkTeacherResponse(request.getEmail(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create teacher"));
                }
            }

            ApiResponse<List<CreateBulkTeacherResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Teachers created", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkTeacherResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create teachers", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All post request for manage account - Student ///////////////
    // Create a new student
    @PostMapping("/create-student")
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(@RequestBody CreateStudentRequest request) {
        try {
            StudentDto studentDto = studentDtoConverter.convert(studentService.createStudent(request)); // Chuyển sang
                                                                                                        // DTO

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student created successfully",
                    studentDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a list of students
    @PostMapping("/create-bulk-students")
    public ResponseEntity<ApiResponse<List<CreateBulkStudentResponse>>> createBulkStudents(
            @RequestBody List<CreateStudentRequest> requests) {
        try {
            List<CreateBulkStudentResponse> responses = new ArrayList<>();

            for (CreateStudentRequest request : requests) {
                try {
                    StudentDto studentDto = studentDtoConverter.convert(studentService.createStudent(request)); // Chuyển
                                                                                                                // sang
                                                                                                                // DTO
                    responses.add(new CreateBulkStudentResponse(studentDto.email(), HttpStatus.OK.value(),
                            "Student created successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkStudentResponse(request.getEmail(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkStudentResponse(request.getEmail(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create student"));
                }
            }

            ApiResponse<List<CreateBulkStudentResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Students created", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkStudentResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create students", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All put request for manage account - Teacher ///////////////
    /// Update a teacher information
    @PutMapping("/update-teacher")
    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacher(@RequestBody UpdateTeacherRequest request) {
        try {
            TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.updateTeacher(request));
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Teacher updated successfully",
                    teacherDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            ApiResponse<TeacherDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to update teacher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a list of teachers information
    @PutMapping("/update-teachers")
    public ResponseEntity<ApiResponse<List<UpdateBulkTeacherResponse>>> updateTeachers(
            @RequestBody List<UpdateTeacherRequest> requests) {
        try {
            List<UpdateBulkTeacherResponse> responses = new ArrayList<>();

            for (UpdateTeacherRequest request : requests) {
                try {
                    TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.updateTeacher(request));
                    responses.add(new UpdateBulkTeacherResponse(teacherDto.id(), HttpStatus.OK.value(),
                            "Teacher updated successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new UpdateBulkTeacherResponse(request.getUserId(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new UpdateBulkTeacherResponse(request.getUserId(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update teacher"));
                }
            }
            ApiResponse<List<UpdateBulkTeacherResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Teachers updated", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UpdateBulkTeacherResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update teachers", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All put request for manage account - Student ///////////////

    // Update a student information
    @PutMapping("/update-student")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(@RequestBody UpdateStudentRequest request) {
        try {

            StudentDto studentDto = studentDtoConverter.convert(studentService.updateStudent(request));
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student updated successfully",
                    studentDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to update student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a list of students information
    @PutMapping("/update-students")
    public ResponseEntity<ApiResponse<List<UpdateBulkStudentResponse>>> updateStudents(
            @RequestBody List<UpdateStudentRequest> requests) {
        try {
            List<UpdateBulkStudentResponse> responses = new ArrayList<>();

            for (UpdateStudentRequest request : requests) {
                try {
                    StudentDto studentDto = studentDtoConverter.convert(studentService.updateStudent(request));
                    responses.add(new UpdateBulkStudentResponse(studentDto.id(), HttpStatus.OK.value(),
                            "Student updated successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new UpdateBulkStudentResponse(request.getUserId(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new UpdateBulkStudentResponse(request.getUserId(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update student"));
                }
            }
            ApiResponse<List<UpdateBulkStudentResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Students updated", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UpdateBulkStudentResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update students", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All delete request for manage account - Teacher///
    /////////////// //////////////
    @DeleteMapping("/delete-teacher/{teacherId}")
    public ResponseEntity<ApiResponse<Void>> deleteTeacher(@PathVariable String teacherId) {
        try {
            teacherService.deleteTeacher(teacherId);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "Teacher deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to delete teacher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All delete request for manage account - Student ///////////////

    // Delete student by Id
    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> deleteStudentByStudentId(@PathVariable String id) {
        try {
            studentService.getStudentById(id);

            // Xóa điểm của sinh viên
            sheetMarkService.deleteAllSheetMarkOfStudentById(id);

            // Xóa sinh viên khỏi tất cả các lớp
            courseClassService.removeStudentFromAllClasses(id);

            // Xóa sinh viên
            studentService.deleteStudentById(id);

            // Phản hồi thành công
            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Student deleted successfully",
                    null);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalStateException | IllegalArgumentException e) {

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            ApiResponse<StudentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to delete student", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
