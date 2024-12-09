package com.hcmut.gradeportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hcmut.gradeportal.entities.enums.Role;
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
			LocalDateTime now = LocalDateTime.now();

			// Kiểm tra trong bảng Admin
			Admin admin = adminRepository.findByEmail(email);
			if (admin != null) {
				admin.setLastLogin(now); 
        adminRepository.save(admin);
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}

			// Kiểm tra trong bảng Teacher
			Teacher teacher = teacherRepository.findByEmail(email);
			if (teacher != null) {
				teacher.setLastLogin(now); 
        teacherRepository.save(teacher);
				authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
			}

			// Kiểm tra trong bảng Student
			Student student = studentRepository.findByEmail(email);
			if (student != null) {
        student.setLastLogin(now);
				studentRepository.save(student);
				authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
			}

			// Nếu không tìm thấy người dùng trong tất cả các bảng thì cấp cho role Student
			if (authorities.isEmpty()) {
				Student newStudent = new Student();
				newStudent.setEmail(email);
				newStudent.setGivenName(capitalizeFirstLetter(oAuth2User.getAttribute("given_name")));
				newStudent.setFamilyName(capitalizeFirstLetter(oAuth2User.getAttribute("family_name")));
				newStudent.setRole(Role.STUDENT);
				studentRepository.save(newStudent);
				authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
			}

			// Thêm các thuộc tính quan trọng có trong database
			Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
			attributes.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList());
			if (admin != null) attributes.put("adminId", admin.getId());
			if (teacher != null) attributes.put("teacherId", teacher.getId());
			if (student != null) attributes.put("studentId", student.getId());

			return new CustomOAuth2User(email, authorities, attributes);
	}

	// Hàm giúp chuyển chữ cái đầu tiên thành chữ hoa, phần còn lại thành chữ thường
	// Giúp chuyển từ định dạng tên của google sang định dạng tên dùng trong hệ thống
	private String capitalizeFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
        return str;
    }
    return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
	}
}

