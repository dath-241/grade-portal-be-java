package com.hcmut.gradeportal.dtos.admin;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import com.hcmut.gradeportal.entities.Admin;

@Component
public class AdminDtoConverter {
    public AdminDtoConverter() {
    }

    public AdminDto convert(Admin from) {
        return new AdminDto(
                from.getId(),
                from.getRole(),
                from.getEmail(),
                from.getFamilyName(),
                from.getGivenName(),
                from.getPhone(),
                from.getFaculty(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                from.getLastLogin());
    }

    public List<AdminDto> convert(List<Admin> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Optional<AdminDto> convert(Optional<Admin> from) {
        return from.map(this::convert);
    }
}
