package com.hcmut.gradeportal.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hcmut.gradeportal.config.GoogleOAuthConfig;
import com.hcmut.gradeportal.dtos.auth.response.LoginResponse;
import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.repositories.AdminRepository;
import com.hcmut.gradeportal.response.ApiResponse;
import com.hcmut.gradeportal.security.jwt.JwtService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/")
public class HomeController {
    private final AdminRepository adminRepository;

    private final JwtService jwtService;

    private final GoogleOAuthConfig googleOAuthConfig;

    public HomeController(AdminRepository adminRepository, JwtService jwtService, GoogleOAuthConfig googleOAuthConfig) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.googleOAuthConfig = googleOAuthConfig;
    }

    @GetMapping
    public String home() {
        return "Welcome to Grade Portal!";
    }

    @GetMapping("/test_security")
    public String testSecurity() {
        return "Security is working!";
    }

    @GetMapping("/login/redirect")
    public ResponseEntity<ApiResponse<LoginResponse>> googleLoginRedirect(@RequestParam("code") String code) {
        String clientId = googleOAuthConfig.getClientId();
        String clientSecret = googleOAuthConfig.getClientSecret();
        String redirectUri = googleOAuthConfig.getRedirectUri();

        System.out.println("Client ID: " + clientId);
        System.out.println("Client Secret: " + clientSecret);
        System.out.println("Redirect URI: " + redirectUri);

        try {
            // 1. Đổi mã "code" lấy Access Token
            String tokenUrl = "https://oauth2.googleapis.com/token";
            RestTemplate restTemplate = new RestTemplate();

            // Tạo body request
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("code", code);
            body.add("client_id", clientId); // Lấy từ file .yaml
            body.add("client_secret", clientSecret); // Lấy từ file .yaml
            body.add("redirect_uri", redirectUri); // Lấy từ file .yaml
            body.add("grant_type", "authorization_code");

            // Tạo header request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Tạo HttpEntity với body và headers
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            // Gửi request và nhận response
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> tokenResponse = responseEntity.getBody();

            String accessToken = (String) tokenResponse.get("access_token");

            // Lấy và in ra idToken
            String idToken = (String) tokenResponse.get("id_token");
            System.out.println("ID Token: " + idToken);

            // 2. Lấy thông tin người dùng từ Access Token
            String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequest,
                    Map.class);

            @SuppressWarnings("unchecked")
            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");

            // 3. Tìm admin hợp lệ trong database
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            if (adminOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null));
            }

            Admin admin = adminOptional.get();

            // 4. Tạo JWT token
            String token = jwtService.generateToken(admin.getId(), admin.getRole());

            // 5. Tạo đối tượng LoginResponse
            LoginResponse response = new LoginResponse();
            response.setId(admin.getId());
            response.setEmail(admin.getEmail());
            response.setFamilyName(admin.getFamilyName());
            response.setGivenName(admin.getGivenName());
            response.setRole(admin.getRole().toString());
            response.setFaculty(admin.getFaculty());
            response.setLastLogin(admin.getLastLogin());
            response.setToken(token);
            response.setIdToken(idToken);

            // 6. Cập nhật thời gian đăng nhập cuối
            admin.setLastLogin(LocalDateTime.now());
            adminRepository.save(admin);

            return ResponseEntity.ok(
                    new ApiResponse<>(HttpStatus.OK.value(), "Login successful", response));

        } catch (Exception e) {
            e.printStackTrace(); // Log chi tiết lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/error")
    public String error() {
        return "Something went wrong";
    }
}
