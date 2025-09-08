package com.educonnect.student_service.dto;

import java.util.List;

public class StudentEditDto {
    private String id;
    private String name;
    private String cgpa;
    private String major;
    private String minBudget;
    private String maxBudget;
    private String ieltsScore;
    private String toeflScore;
    private String greScore;
    private String phoneNumber;
    private String email;
    private String country;
    private String preferredCountry;
    private List<FieldDto> fields;

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

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIeltsScore() {
        return ieltsScore;
    }

    public void setIeltsScore(String ieltsScore) {
        this.ieltsScore = ieltsScore;
    }

    public String getToeflScore() {
        return toeflScore;
    }

    public void setToeflScore(String toeflScore) {
        this.toeflScore = toeflScore;
    }

    public String getGreScore() {
        return greScore;
    }

    public void setGreScore(String greScore) {
        this.greScore = greScore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPreferredCountry() {
        return preferredCountry;
    }

    public void setPreferredCountry(String preferredCountry) {
        this.preferredCountry = preferredCountry;
    }

    public String getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(String minBudget) {
        this.minBudget = minBudget;
    }

    public String getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(String maxBudget) {
        this.maxBudget = maxBudget;
    }

    public void setFields(List<FieldDto> fields) {
        this.fields = fields;
    }

    public List<FieldDto> getFields() {
        return fields;
    }
}
