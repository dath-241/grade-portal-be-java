package com.hcmut.gradeportal.controller.auth;

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
	@GetMapping("/")
	public String home(){
		return "wellcome home";
	}

	@GetMapping("/user")
	public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
		return oAuth2User.getAttributes();
	}

	@GetMapping("/login?error")
	public String error(){
		return "Something went wrong";
	}
}
