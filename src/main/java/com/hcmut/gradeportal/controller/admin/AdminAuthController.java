package com.hcmut.gradeportal.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.admin.AdminDto;
import com.hcmut.gradeportal.dtos.admin.AdminDtoConverter;
import com.hcmut.gradeportal.dtos.auth.request.AuthRequestForAdmin;
import com.hcmut.gradeportal.dtos.auth.response.AuthResponse;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.utils.CurrentUser;
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

    // // Login for Admin with token of google
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse<AdminDto>>> login(@RequestBody AuthRequestForAdmin request) {
        try {
            AuthResponse<Admin> authResponse = adminService.login(request.getIdToken());

            AuthResponse<AdminDto> response = new AuthResponse<>( // Convert to AdminDto
                    authResponse.getToken(), adminDtoConverter.convert(authResponse.getUser()), authResponse.getRole());
            ApiResponse<AuthResponse<AdminDto>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Login success",
                    response);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<AuthResponse<AdminDto>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<AuthResponse<AdminDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////// All Get request for admin auth /////////////////
    // Get own information
    @GetMapping("/get-info")
    public ResponseEntity<ApiResponse<AdminDto>> getOwnInfo() {
        try {
            String userId = CurrentUser.getUserId();

            AdminDto adminDto = adminDtoConverter.convert(adminService.getAdminById(userId));
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
}
