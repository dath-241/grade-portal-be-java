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

import com.hcmut.gradeportal.dtos.student.StudentDto;
import com.hcmut.gradeportal.dtos.student.StudentDtoConverter;
import com.hcmut.gradeportal.dtos.student.reponse.CreateBulkStudentReponse;
import com.hcmut.gradeportal.dtos.student.request.CreateStudentRequest;
import com.hcmut.gradeportal.dtos.student.request.GetStudentRequest;
import com.hcmut.gradeportal.dtos.teacher.TeacherDto;
import com.hcmut.gradeportal.dtos.teacher.TeacherDtoConverter;
import com.hcmut.gradeportal.dtos.teacher.reponse.CreateBulkTeacherReponse;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;
import com.hcmut.gradeportal.dtos.teacher.request.GetTeacherRequest;
import com.hcmut.gradeportal.reponse.ApiResponse;
import com.hcmut.gradeportal.service.StudentService;
import com.hcmut.gradeportal.service.TeacherService;

@RestController
@RequestMapping("/admin/manage-account")
public class AdminManageAccountController {
    private final TeacherDtoConverter teacherDtoConverter;
    private final StudentDtoConverter studentDtoConverter;

    private final TeacherService teacherService;
    private final StudentService studentService;

    public AdminManageAccountController(TeacherService teacherService, StudentService studentService,
            TeacherDtoConverter teacherDtoConverter, StudentDtoConverter studentDtoConverter) {
        this.teacherDtoConverter = teacherDtoConverter;
        this.studentDtoConverter = studentDtoConverter;

        this.teacherService = teacherService;
        this.studentService = studentService;
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
    public ResponseEntity<ApiResponse<List<CreateBulkTeacherReponse>>> createBulkTeachers(
            @RequestBody List<CreateTeacherRequest> requests) {
        try {
            List<CreateBulkTeacherReponse> responses = new ArrayList<>();

            for (CreateTeacherRequest request : requests) {
                try {
                    TeacherDto teacherDto = teacherDtoConverter.convert(teacherService.createTeacher(request)); // Chuyển
                                                                                                                // sang
                                                                                                                // DTO
                    responses.add(new CreateBulkTeacherReponse(teacherDto.email(), HttpStatus.OK.value(),
                            "Teacher created successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkTeacherReponse(request.getEmail(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkTeacherReponse(request.getEmail(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create teacher"));
                }
            }

            ApiResponse<List<CreateBulkTeacherReponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Teachers created", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkTeacherReponse>> response = new ApiResponse<>(
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
    public ResponseEntity<ApiResponse<List<CreateBulkStudentReponse>>> createBulkStudents(
            @RequestBody List<CreateStudentRequest> requests) {
        try {
            List<CreateBulkStudentReponse> responses = new ArrayList<>();

            for (CreateStudentRequest request : requests) {
                try {
                    StudentDto studentDto = studentDtoConverter.convert(studentService.createStudent(request)); // Chuyển
                                                                                                                // sang
                                                                                                                // DTO
                    responses.add(new CreateBulkStudentReponse(studentDto.email(), HttpStatus.OK.value(),
                            "Student created successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkStudentReponse(request.getEmail(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkStudentReponse(request.getEmail(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create student"));
                }
            }

            ApiResponse<List<CreateBulkStudentReponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Students created", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkStudentReponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create students", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////////// All put request for manage account - Teacher ///////////////

    /////////////// All put request for manage account - Student ///////////////

    /////////////// All delete request for manage account - Teacher ///////////////

    /////////////// All delete request for manage account - Student ///////////////

}
