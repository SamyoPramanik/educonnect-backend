package com.educonnect.education_service.dto;

import java.util.List;

public class ProfessorDetailsDto {
    private String id;
    private String name;
    private String bio;
    private String country;
    private String email;
    private String url;
    private List<FieldDto> fields;
    private List<ExperienceDto> experiences;
    private List<PaperDto> papers;

    public List<FieldDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldDto> fields) {
        this.fields = fields;
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

    public List<ExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<PaperDto> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperDto> papers) {
        this.papers = papers;
    }

}
