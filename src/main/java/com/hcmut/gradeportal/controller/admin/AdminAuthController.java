package com.hcmut.gradeportal.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.admin.AdminDto;
import com.hcmut.gradeportal.dtos.admin.AdminDtoConverter;
import com.hcmut.gradeportal.dtos.halloffame.HallOfFameRequest;
import com.hcmut.gradeportal.entities.HallOfFame;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.AdminService;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    private final AdminDtoConverter adminDtoConverter;
    private final AdminService adminService;

    public AdminAuthController(AdminDtoConverter adminDtoConverter, AdminService adminService) {
        this.adminDtoConverter = adminDtoConverter;
        this.adminService = adminService;
    }

    ///////////////// All Get request for admin auth /////////////////
    // Get Personal Information
    @GetMapping("/get-info/{id}")
    public ResponseEntity<ApiResponse<AdminDto>> getPersonalInfo(@PathVariable String id) {
        try {
            AdminDto adminDto = adminDtoConverter.convert(adminService.getAdminById(id));
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Get personal information",
                    adminDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Post request for admin auth /////////////////

    ///////////////// All Put request for admin auth /////////////////

    ///////////////// All Delete request for admin auth /////////////////
    @GetMapping("/hall")
    public ResponseEntity<ApiResponse<List<HallOfFame>>> getAllClasses(@RequestBody HallOfFameRequest request) {
        try {
            List<HallOfFame> courseClassDtos = adminService.halloffame(request);
            ApiResponse<List<HallOfFame>> response = new ApiResponse<>(HttpStatus.OK.value(), "Get all classes",
                    courseClassDtos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<HallOfFame>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
