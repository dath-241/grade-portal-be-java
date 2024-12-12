package com.hcmut.gradeportal.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.dtos.admin.AdminDto;
import com.hcmut.gradeportal.dtos.admin.AdminDtoConverter;
import com.hcmut.gradeportal.dtos.admin.request.CreateAdminRequest;
import com.hcmut.gradeportal.dtos.admin.request.GetAdminRequest;
import com.hcmut.gradeportal.dtos.admin.request.UpdateAdminRequest;
import com.hcmut.gradeportal.dtos.admin.response.CreateBulkAdminResponse;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.service.AdminService;

@RestController
@RequestMapping("/admin/manage-admin")
public class AdminManageAdminController {
    private final AdminService adminService;

    private final AdminDtoConverter adminDtoConverter;

    public AdminManageAdminController(AdminService adminService, AdminDtoConverter adminDtoConverter) {
        this.adminService = adminService;
        this.adminDtoConverter = adminDtoConverter;
    }

    ///////////////// All Get request for manage admin /////////////////
    /// // Lấy danh sách tất cả Admins
    @GetMapping("/get-all-admins")
    public ResponseEntity<ApiResponse<List<AdminDto>>> getAllAdmins() {
        try {
            List<AdminDto> adminDtos = adminDtoConverter.convert(adminService.getAllAdmins()); // Chuyển sang DTO

            ApiResponse<List<AdminDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Admins retrieved",
                    adminDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<AdminDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve admins", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy danh sách Admins dựa theo điều kiện
    @GetMapping("/get-admins-by-specification")
    public ResponseEntity<ApiResponse<List<AdminDto>>> getAdminsBySpecification(@RequestBody GetAdminRequest request) {
        try {
            // Lấy danh sách admin dựa theo điều kiện
            List<AdminDto> adminDtos = adminDtoConverter.convert(adminService.getAdminsBySpecification(request)); // Chuyển
                                                                                                                  // sang
                                                                                                                  // DTO

            // Tạo response thành công
            ApiResponse<List<AdminDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Admins retrieved",
                    adminDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Tạo response lỗi
            ApiResponse<List<AdminDto>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve admins", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////// All Post request for manage admin //////////////////
    // Tạo một Admin mới
    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponse<AdminDto>> createAdmin(@RequestBody CreateAdminRequest request) {
        try {
            Admin admin = adminService.createAdmin(request);
            AdminDto adminDto = adminDtoConverter.convert(admin);

            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Admin created successfully",
                    adminDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create admin", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Tạo danh sách Admins
    @PostMapping("/create-bulk-admins")
    public ResponseEntity<ApiResponse<List<CreateBulkAdminResponse>>> createBulkAdmins(
            @RequestBody List<CreateAdminRequest> requests) {
        try {
            List<CreateBulkAdminResponse> responses = new ArrayList<>();

            for (CreateAdminRequest request : requests) {
                try {
                    Admin admin = adminService.createAdmin(request);
                    AdminDto adminDto = adminDtoConverter.convert(admin);
                    responses.add(new CreateBulkAdminResponse(adminDto.email(), HttpStatus.OK.value(),
                            "Admin created successfully"));
                } catch (IllegalArgumentException e) {
                    responses.add(new CreateBulkAdminResponse(request.getEmail(), HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()));
                } catch (Exception e) {
                    responses.add(new CreateBulkAdminResponse(request.getEmail(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create admin"));
                }
            }

            ApiResponse<List<CreateBulkAdminResponse>> response = new ApiResponse<>(HttpStatus.OK.value(),
                    "Admins created", responses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CreateBulkAdminResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create admins", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////// All Put request for manage admin //////////////////
    // Cập nhật thông tin Admin theo ID
    // Update an admin information
    @PutMapping("/update-admin")
    public ResponseEntity<ApiResponse<AdminDto>> updateAdmin(@RequestBody UpdateAdminRequest request) {
        try {

            AdminDto adminDto = adminDtoConverter.convert(adminService.updateAdmin(request));
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Admin updated successfully",
                    adminDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to update admin", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////// All Delete request for manage admin //////////////////
    // Xóa Admin theo ID
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<ApiResponse<AdminDto>> deleteAdmin(@PathVariable String id) {
        try {
            AdminDto adminDto = adminDtoConverter.convert(adminService.deleteAdmin(id));
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Admin deleted successfully",
                    adminDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<AdminDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to delete admin", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
