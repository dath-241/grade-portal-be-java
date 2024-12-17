package com.hcmut.gradeportal.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated. Cannot retrieve user ID.");
        }
        return authentication.getName();
    }

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities() == null) {
            throw new IllegalStateException("User is not authenticated or has no roles. Cannot retrieve role.");
        }
        return authentication.getAuthorities().iterator().next().getAuthority();
    }
}