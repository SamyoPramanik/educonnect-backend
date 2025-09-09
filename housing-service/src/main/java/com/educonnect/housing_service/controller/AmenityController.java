package com.educonnect.housing_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.housing_service.dto.AmenityDto;
import com.educonnect.housing_service.service.AmenityService;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @PostMapping("/create")
    public ResponseEntity<AmenityDto> createAmenity(@RequestBody AmenityDto amenityDto) {
        AmenityDto newAmenityDto = amenityService.createAmenity(amenityDto);
        return ResponseEntity.ok(newAmenityDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityDto> updateAmenity(@PathVariable String id, @RequestBody AmenityDto amenityDto) {
        AmenityDto updatedAmenity = amenityService.updateAmenity(id, amenityDto);
        if (updatedAmenity != null) {
            return ResponseEntity.ok(updatedAmenity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AmenityDto>> getAllAmenities() {
        java.util.List<AmenityDto> amenityDtos = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenityDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDto> getAmenityById(@PathVariable String id) {
        AmenityDto amenityDto = amenityService.getAmenityById(id);
        if (amenityDto != null) {
            return ResponseEntity.ok(amenityDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable String id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }

}
