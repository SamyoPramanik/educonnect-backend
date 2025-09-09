package com.educonnect.housing_service.util;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.educonnect.housing_service.dto.UniversityDto;

@Component
public class EducationUtil {
    public UniversityDto getUniversityById(UUID universityId, String token) {
        try {
            WebClient webClient = WebClient.create("http://education-service:8080");
            return webClient.get()
                    .uri("/api/university/{universityId}", universityId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(UniversityDto.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
