package com.educonnect.student_service.util;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.educonnect.student_service.dto.FieldDto;
import com.educonnect.student_service.dto.ProfessorDetailsDto;
import com.educonnect.student_service.dto.ScholarshipDto;
import com.educonnect.student_service.dto.UniversityDto;

@Component
public class EducationUtil {
    public ProfessorDetailsDto getProfessorById(UUID professorId, String token) {
        try {
            WebClient webClient = WebClient.create("http://education-service:8080");
            return webClient.get()
                    .uri("/api/professor/{professorId}", professorId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(ProfessorDetailsDto.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }
    }

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

    public ScholarshipDto getScholarshipById(UUID scholarshipId, String token) {
        try {
            WebClient webClient = WebClient.create("http://education-service:8080");
            return webClient.get()
                    .uri("/api/scholarship/{scholarshipId}", scholarshipId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(ScholarshipDto.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public FieldDto getFieldById(UUID fieldId, String token) {
        try {
            WebClient webClient = WebClient.create("http://education-service:8080");
            return webClient.get()
                    .uri("/api/field/{fieldId}", fieldId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(FieldDto.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
