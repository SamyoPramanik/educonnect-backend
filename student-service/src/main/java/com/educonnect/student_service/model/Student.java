package com.educonnect.student_service.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private double cgpa;

    private String major;

    private double budget;

    private double ieltsScore;
    private double toeflScore;
    private double greScore;
    private String phoneNumber;
    private String email;
    private String country;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getIeltsScore() {
        return ieltsScore;
    }

    public void setIeltsScore(double ieltsScore) {
        this.ieltsScore = ieltsScore;
    }

    public double getToeflScore() {
        return toeflScore;
    }

    public void setToeflScore(double toeflScore) {
        this.toeflScore = toeflScore;
    }

    public double getGreScore() {
        return greScore;
    }

    public void setGreScore(double greScore) {
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
}
