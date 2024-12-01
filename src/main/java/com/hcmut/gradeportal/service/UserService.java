package com.hcmut.gradeportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hcmut.gradeportal.entities.Admin;
import com.hcmut.gradeportal.entities.Student;
import com.hcmut.gradeportal.entities.Teacher;
import com.hcmut.gradeportal.entities.UserDetail.CustomOAuth2User;
import com.hcmut.gradeportal.repositories.AdminRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;

@Service
public class UserService extends DefaultOAuth2UserService{
	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	private final TeacherRepository teacherRepository;

	public UserService(StudentRepository studentRepository, AdminRepository adminRepository, TeacherRepository teacherRepository) {
					this.studentRepository = studentRepository;
					this.adminRepository = adminRepository;
					this.teacherRepository = teacherRepository;
	}
  
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			OAuth2User oAuth2User = super.loadUser(userRequest);
			String email = oAuth2User.getAttribute("email");

			List<GrantedAuthority> authorities = new ArrayList<>();

			// Kiểm tra trong bảng Admin
			Admin admin = adminRepository.findByEmail(email);
			if (admin != null) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}

			// Kiểm tra trong bảng Teacher
			Teacher teacher = teacherRepository.findByEmail(email);
			if (teacher != null) {
					authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
			}

			// Kiểm tra trong bảng Student
			Student student = studentRepository.findByEmail(email);
			if (student != null) {
					authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
			}

			// Nếu không tìm thấy người dùng trong tất cả các bảng
			if (authorities.isEmpty()) {
					throw new OAuth2AuthenticationException("User not found in any role tables!");
			}

			// Trả về CustomOAuth2User với tất cả các quyền đã tìm thấy
			return new CustomOAuth2User(email, authorities, oAuth2User.getAttributes());
	}

}
