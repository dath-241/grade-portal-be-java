package com.hcmut.gradeportal.controller.auth;

import org.springframework.web.bind.annotation.RestController;

import com.hcmut.gradeportal.entities.UserDetail.CustomOAuth2User;

import com.hcmut.gradeportal.response.ApiResponse;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
	@GetMapping("/")
	public String home() {
		return "wellcome home";
	}

	@GetMapping("/user")
	public ResponseEntity<ApiResponse<Map<String, Object>>> getUser(
			@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		try {
			Map<String, Object> userAttributes = customOAuth2User.getAttributes();
			ApiResponse<Map<String, Object>> response = new ApiResponse<>(HttpStatus.OK.value(),
					"User information retrieved successfully", userAttributes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			ApiResponse<Map<String, Object>> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					e.getMessage(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/error")
	public String error() {
		return "Something went wrong";
	}
}
