package com.educonnect.housing_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.housing_service.dto.AmenityDto;
import com.educonnect.housing_service.model.Amenity;
import com.educonnect.housing_service.repository.AmenityRepository;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public AmenityDto createAmenity(AmenityDto amenityDto) {
        Amenity amenity = new Amenity();
        amenity.setName(amenityDto.getName());
        Amenity savedAmenity = amenityRepository.save(amenity);
        amenityDto.setId(savedAmenity.getId().toString());
        return amenityDto;
    }

    public AmenityDto getAmenityById(String id) {
        return amenityRepository.findById(UUID.fromString(id))
                .map(amenity -> {
                    AmenityDto dto = new AmenityDto();
                    dto.setId(amenity.getId().toString());
                    dto.setName(amenity.getName());
                    return dto;
                })
                .orElse(null);
    }

    public List<AmenityDto> getAllAmenities() {
        List<Amenity> amenities = amenityRepository.findAll();
        return amenities.stream().map(amenity -> {
            AmenityDto dto = new AmenityDto();
            dto.setId(amenity.getId().toString());
            dto.setName(amenity.getName());
            return dto;
        }).toList();
    }

    public AmenityDto updateAmenity(String id, AmenityDto amenityDto) {
        return amenityRepository.findById(UUID.fromString(id))
                .map(amenity -> {
                    amenity.setName(amenityDto.getName());
                    Amenity updatedAmenity = amenityRepository.save(amenity);
                    amenityDto.setId(updatedAmenity.getId().toString());
                    return amenityDto;
                })
                .orElse(null);
    }

    public void deleteAmenity(String id) {
        amenityRepository.deleteById(UUID.fromString(id));
    }
}
