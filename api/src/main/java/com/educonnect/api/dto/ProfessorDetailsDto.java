package com.educonnect.api.dto;

import java.util.List;
import java.util.Map;

public class ProfessorDetailsDto {
    private String id;
    private String name;
    private String bio;
    private String country;
    private String email;
    private String url;
    private List<String> fields;
    private List<Map<String, String>> experiences;
    private List<Map<String, String>> papers;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
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

    public List<Map<String, String>> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Map<String, String>> experiences) {
        this.experiences = experiences;
    }

    public List<Map<String, String>> getPapers() {
        return papers;
    }

    public void setPapers(List<Map<String, String>> papers) {
        this.papers = papers;
    }

}
