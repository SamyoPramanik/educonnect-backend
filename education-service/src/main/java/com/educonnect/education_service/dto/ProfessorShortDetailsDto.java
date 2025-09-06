package com.educonnect.education_service.dto;

public class ProfessorShortDetailsDto {
    private String id;
    private String name;
    private String bio;
    private String university;
    private String country;
    private String publicationCount;

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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublicationCount() {
        return publicationCount;
    }

    public void setPublicationCount(String publicationCount) {
        this.publicationCount = publicationCount;
    }

}
