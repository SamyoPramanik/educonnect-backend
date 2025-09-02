package com.educonnect.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfessorDto {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "URL is required")
    private String url;

    public ProfessorDto(String id, @NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Bio is required") String bio,
            @NotBlank(message = "Country is required") String country,
            @NotBlank(message = "Email is required") String email, @NotBlank(message = "URL is required") String url) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.country = country;
        this.email = email;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
