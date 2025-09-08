package com.educonnect.housing_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.housing_service.service.AmenityService;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }
}
