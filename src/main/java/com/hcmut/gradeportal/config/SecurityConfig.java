package com.hcmut.gradeportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hcmut.gradeportal.response.CustomAccessDeniedHandler;
import com.hcmut.gradeportal.security.jwt.JwtAuthenticationFilter;

import org.springframework.lang.NonNull;

@Configuration
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(@NonNull CorsRegistry registry) {
                                registry.addMapping("/**")
                                                .allowedOrigins("http://localhost:3000")
                                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                                .allowedHeaders("*")
                                                .allowCredentials(true)
                                                .maxAge(3600);
                        }
                };
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF (REST API không cần CSRF)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                                .authorizeHttpRequests(auth -> auth
                                                // Các endpoint không yêu cầu xác thực
                                                .requestMatchers("/", "/health_check", "/login", "/error",
                                                                "/oauth2/authorization/google",
                                                                "/auth/login", "/admin/auth/login",
                                                                "/student/auth/login", "/teacher/auth/login")
                                                .permitAll()
                                                // OAuth2 login
                                                .requestMatchers("/login/redirect", "/login/error").permitAll()
                                                // Các endpoint yêu cầu xác thực và phân quyền
                                                .requestMatchers("/admin/**", "/init-data").hasAuthority("ADMIN")
                                                .requestMatchers("/teacher/**").hasAnyAuthority("ADMIN", "TEACHER")
                                                .requestMatchers("/student/**").hasAnyAuthority("ADMIN", "STUDENT")
                                                .requestMatchers("/hall-of-fame",
                                                                "/hall-of-fame/get-all")
                                                .hasAnyAuthority("ADMIN", "STUDENT", "TEACHER")
                                                // Các request còn lại cần xác thực
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .defaultSuccessUrl("/login/redirect", true)
                                                .failureUrl("/login/error"))
                                // Thêm JwtAuthenticationFilter vào chuỗi filter
                                .exceptionHandling(exception -> exception
                                                .accessDeniedHandler(new CustomAccessDeniedHandler()))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
