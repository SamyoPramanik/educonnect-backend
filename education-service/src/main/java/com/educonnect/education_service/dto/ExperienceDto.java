package com.educonnect.education_service.dto;

public class ExperienceDto {
    private String id;

    private String title;
    private String universityId;
    private String startYear;
    private String endYear;
    private String professorId;

    public ExperienceDto(String id, String title, String universityId, String startYear, String endYear,
            String professorId) {
        this.id = id;
        this.title = title;
        this.universityId = universityId;
        this.startYear = startYear;
        this.endYear = endYear;
        this.professorId = professorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
}
