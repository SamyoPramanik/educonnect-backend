package com.educonnect.education_service.util;

import org.springframework.web.reactive.function.client.WebClient;

import com.educonnect.education_service.dto.UserDto;

public class UserUtil {
    public UserDto getUser(String token) {
        try {
            WebClient webClient = WebClient.create("http://auth-service:8080");
            return webClient.get()
                    .uri("/auth/userinfo")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }

    }
}
