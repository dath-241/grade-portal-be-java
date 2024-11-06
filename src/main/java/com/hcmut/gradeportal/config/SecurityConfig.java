package com.hcmut.gradeportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hcmut.gradeportal.security.jwt.JwtAuthenticationFilter;

import org.springframework.lang.NonNull;

@Configuration
@EnableWebSecurity
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
                registry.addMapping("**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed request headers
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF, phù hợp cho API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                                                                                                              // session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/auth/login", "/teacher/auth/login", "/student/auth/login").permitAll() // Cho
                                                                                                                        // phép
                                                                                                                        // truy
                                                                                                                        // cập
                                                                                                                        // vào
                                                                                                                        // các
                                                                                                                        // endpoint
                                                                                                                        // login
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Quyền ADMIN cho phép truy cập vào tất cả
                                                                            // các endpoint
                        .requestMatchers("/teacher/**").hasAnyAuthority("ADMIN", "TEACHER") // Cho phép quyền ADMIN và
                                                                                            // TEACHER vào các endpoint
                                                                                            // của teacher
                        .requestMatchers("/student/**").hasAnyAuthority("ADMIN", "STUDENT") // Cho phép quyền ADMIN và
                                                                                            // STUDENT vào các endpoint
                                                                                            // của student
                        .anyRequest().authenticated() // Các yêu cầu còn lại cần xác thực
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Thêm
                                                                                                       // JwtAuthenticationFilter
                                                                                                       // trước
                                                                                                       // UsernamePasswordAuthenticationFilter

        return http.build();
    }

}
