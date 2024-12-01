package com.hcmut.gradeportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hcmut.gradeportal.repositories.AdminRepository;
import com.hcmut.gradeportal.repositories.StudentRepository;
import com.hcmut.gradeportal.repositories.TeacherRepository;
import com.hcmut.gradeportal.service.UserService;



@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	private final TeacherRepository teacherRepository;

	public SecurityConfig(StudentRepository studentRepository, AdminRepository adminRepository, TeacherRepository teacherRepository) {
					this.studentRepository = studentRepository;
					this.adminRepository = adminRepository;
					this.teacherRepository = teacherRepository;
	}

    @Bean
    public UserService userService() {
        return new UserService(studentRepository, adminRepository, teacherRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/teacher/**").hasRole("TEACHER")
                .requestMatchers("/student/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(userService()) // Sử dụng CustomOAuth2UserService
                )
                .defaultSuccessUrl("/user", true)
                .failureUrl("/login?error")
            )

            .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}


