package com.hcmut.gradeportal.helper.dataLoader;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmut.gradeportal.dtos.teacher.request.CreateTeacherRequest;


@Component
public class TeacherDataLoader {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public TeacherDataLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public Set<CreateTeacherRequest> loadTeacherData() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:baseData/teacherData.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<Set<CreateTeacherRequest>>() {});
    }
}