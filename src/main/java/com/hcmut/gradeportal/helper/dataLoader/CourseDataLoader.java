package com.hcmut.gradeportal.helper.dataLoader;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmut.gradeportal.dtos.course.request.CreateCourseRequest;

@Component
public class CourseDataLoader {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public CourseDataLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public Set<CreateCourseRequest> loadCourseData() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:baseData/courseData.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<Set<CreateCourseRequest>>() {
        });
    }
}
