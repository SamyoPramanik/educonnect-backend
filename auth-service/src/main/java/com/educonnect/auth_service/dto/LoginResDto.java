package com.educonnect.auth_service.dto;

public class LoginResDto {
    String token;

    public LoginResDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
