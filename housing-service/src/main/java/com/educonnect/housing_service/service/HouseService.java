package com.educonnect.housing_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.housing_service.dto.AmenityDto;
import com.educonnect.housing_service.dto.HouseDto;
import com.educonnect.housing_service.dto.HouseEditDto;
import com.educonnect.housing_service.dto.OwnerDto;
import com.educonnect.housing_service.dto.UniversityDto;
import com.educonnect.housing_service.model.House;
import com.educonnect.housing_service.model.HouseAmenity;
import com.educonnect.housing_service.repository.HouseAmenityRepository;
import com.educonnect.housing_service.repository.HouseRepository;
import com.educonnect.housing_service.util.EducationUtil;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    private final AmenityService amenityService;
    private final HouseAmenityRepository houseAmenityRepository;
    private final OwnerService ownerService;
    private final EducationUtil educationUtil;

    public HouseService(HouseRepository houseRepository, AmenityService amenityService,
            HouseAmenityRepository houseAmenityRepository, OwnerService ownerService, EducationUtil educationUtil) {
        this.houseRepository = houseRepository;
        this.amenityService = amenityService;
        this.houseAmenityRepository = houseAmenityRepository;
        this.ownerService = ownerService;
        this.educationUtil = educationUtil;
    }

    public HouseEditDto createHouse(HouseEditDto houseEditDto) {
        // Convert DTO to entity
        House houseModel = new House();
        houseModel.setTitle(houseEditDto.getTitle());
        houseModel.setDescription(houseEditDto.getDescription());
        houseModel.setType(houseEditDto.getType());
        houseModel.setPricePerMonth(Integer.parseInt(houseEditDto.getPricePerMonth()));
        houseModel.setNumberOfBedrooms(Integer.parseInt(houseEditDto.getNumberOfBedrooms()));
        houseModel.setNumberOfBathrooms(Integer.parseInt(houseEditDto.getNumberOfBathrooms()));
        houseModel.setCountry(houseEditDto.getCountry());
        houseModel.setState(houseEditDto.getState());
        houseModel.setCity(houseEditDto.getCity());
        houseModel.setAddress(houseEditDto.getAddress());
        OwnerDto ownerDto = ownerService.getOwnerById(houseEditDto.getOwnerId());
        if (ownerDto == null) {
            throw new RuntimeException("Owner not found with id: " + houseEditDto.getOwnerId());
        }
        houseModel.setOwnerId(UUID.fromString(houseEditDto.getOwnerId()));
        houseModel.setUniversityId(UUID.fromString(houseEditDto.getUniversityId()));
        houseModel.setDistanceToUniversity(Double.parseDouble(houseEditDto.getDistanceToUniversity()));

        if (houseEditDto.getAmenities() != null) {
            for (AmenityDto amenity : houseEditDto.getAmenities()) {
                AmenityDto amenityDto = amenityService.getAmenityById(amenity.getId());
                if (amenityDto != null) {
                    HouseAmenity houseAmenity = new HouseAmenity();
                    houseAmenity.setAmenityId(UUID.fromString(amenityDto.getId()));
                    houseAmenity.setHouseId(UUID.fromString(houseEditDto.getId()));
                    houseAmenityRepository.save(houseAmenity);
                }
            }
        }

        // Save entity
        var savedHouse = houseRepository.save(houseModel);

        // Convert entity back to DTO
        houseEditDto.setId(savedHouse.getId().toString());

        return houseEditDto;
    }

    public HouseDto getHouseById(String id, String token) {
        return houseRepository.findById(UUID.fromString(id))
                .map(house -> {
                    HouseDto dto = new HouseDto();
                    dto.setId(house.getId().toString());
                    dto.setTitle(house.getTitle());
                    dto.setDescription(house.getDescription());
                    dto.setType(house.getType());
                    dto.setPricePerMonth(String.valueOf(house.getPricePerMonth()));
                    dto.setNumberOfBedrooms(String.valueOf(house.getNumberOfBedrooms()));
                    dto.setNumberOfBathrooms(String.valueOf(house.getNumberOfBathrooms()));
                    dto.setCountry(house.getCountry());
                    dto.setState(house.getState());
                    dto.setCity(house.getCity());
                    dto.setAddress(house.getAddress());
                    dto.setDistanceToUniversity(house.getDistanceToUniversity());

                    OwnerDto ownerDto = ownerService.getOwnerById(house.getOwnerId().toString());
                    dto.setOwnerId(ownerDto.getId());
                    dto.setOwner(ownerDto.getName());
                    dto.setContactEmail(ownerDto.getEmail());
                    dto.setContactPhone(ownerDto.getPhone());
                    dto.setUniversityId(house.getUniversityId().toString());

                    UniversityDto universityDto = educationUtil
                            .getUniversityById(house.getUniversityId(), token);
                    if (universityDto != null) {
                        dto.setUniversity(universityDto.getName());
                    } else {
                        dto.setUniversity("Unknown");
                    }
                    // Fetch and set amenities
                    List<AmenityDto> amenities = new ArrayList<>();
                    List<HouseAmenity> houseAmenities = houseAmenityRepository.findByHouseId(UUID.fromString(id));
                    for (HouseAmenity ha : houseAmenities) {
                        AmenityDto amenityDto = amenityService.getAmenityById(ha.getAmenityId().toString());
                        if (amenityDto != null) {
                            amenities.add(amenityDto);
                        }
                    }
                    dto.setAmenities(amenities);
                    return dto;
                })
                .orElse(null);
    }

    public HouseEditDto updateHouse(String id, HouseEditDto houseEditDto) {
        Optional<House> houseOpt = houseRepository.findById(UUID.fromString(id));
        if (houseOpt.isEmpty()) {
            throw new RuntimeException("House not found with id: " + id);
        } else if (houseEditDto.getOwnerId() == null) {
            throw new RuntimeException("Owner ID is required for update.");
        }

        House house = houseOpt.get();

        house.setTitle(houseEditDto.getTitle());
        house.setDescription(houseEditDto.getDescription());
        house.setType(houseEditDto.getType());
        house.setPricePerMonth(Integer.parseInt(houseEditDto.getPricePerMonth()));
        house.setNumberOfBedrooms(Integer.parseInt(houseEditDto.getNumberOfBedrooms()));
        house.setNumberOfBathrooms(Integer.parseInt(houseEditDto.getNumberOfBathrooms()));
        house.setCountry(houseEditDto.getCountry());
        house.setState(houseEditDto.getState());
        house.setCity(houseEditDto.getCity());
        house.setAddress(houseEditDto.getAddress());
        OwnerDto ownerDto = ownerService.getOwnerById(houseEditDto.getOwnerId());
        if (ownerDto == null) {
            throw new RuntimeException("Owner not found with id: " + houseEditDto.getOwnerId());
        }
        house.setOwnerId(UUID.fromString(ownerDto.getId()));
        house.setUniversityId(UUID.fromString(houseEditDto.getUniversityId()));
        house.setDistanceToUniversity(Double.parseDouble(houseEditDto.getDistanceToUniversity()));

        // Update amenities
        var existingAmenities = houseAmenityRepository.findByHouseId(house.getId());
        houseAmenityRepository.deleteAll(existingAmenities);

        if (houseEditDto.getAmenities() != null) {
            for (AmenityDto amenityDto : houseEditDto.getAmenities()) {
                AmenityDto amenityDto2 = amenityService
                        .getAmenityById(amenityDto.getId());
                if (amenityDto2 != null) {
                    HouseAmenity houseAmenity = new HouseAmenity();
                    houseAmenity.setAmenityId(UUID.fromString(amenityDto2.getId()));
                    houseAmenity.setHouseId(house.getId());
                    houseAmenityRepository.save(houseAmenity);
                }
            }
        }

        House updatedHouse = houseRepository.save(house);
        houseEditDto.setId(updatedHouse.getId().toString());
        return houseEditDto;

    }

    public List<HouseDto> getAllHouses(String token) {
        return houseRepository.findAll().stream()
                .map(house -> {
                    HouseDto dto = new HouseDto();
                    dto.setId(house.getId().toString());
                    dto.setTitle(house.getTitle());
                    dto.setDescription(house.getDescription());
                    dto.setType(house.getType());
                    dto.setPricePerMonth(String.valueOf(house.getPricePerMonth()));
                    dto.setNumberOfBedrooms(String.valueOf(house.getNumberOfBedrooms()));
                    dto.setNumberOfBathrooms(String.valueOf(house.getNumberOfBathrooms()));
                    dto.setCountry(house.getCountry());
                    dto.setState(house.getState());
                    dto.setCity(house.getCity());
                    dto.setAddress(house.getAddress());
                    dto.setDistanceToUniversity(house.getDistanceToUniversity());
                    dto.setOwnerId(house.getOwnerId().toString());

                    OwnerDto ownerDto = ownerService.getOwnerById(house.getOwnerId().toString());
                    dto.setOwner(ownerDto.getName());
                    dto.setContactEmail(ownerDto.getEmail());
                    dto.setContactPhone(ownerDto.getPhone());
                    dto.setUniversityId(house.getUniversityId().toString());

                    UniversityDto universityDto = educationUtil
                            .getUniversityById(house.getUniversityId(), token);
                    if (universityDto != null) {
                        dto.setUniversity(universityDto.getName());
                    } else {
                        dto.setUniversity("Unknown");
                    }

                    // Fetch and set amenities
                    List<AmenityDto> amenities = new ArrayList<>();
                    List<HouseAmenity> houseAmenities = houseAmenityRepository
                            .findByHouseId(house.getId());
                    for (HouseAmenity ha : houseAmenities) {
                        AmenityDto amenityDto = amenityService.getAmenityById(ha.getAmenityId().toString());
                        if (amenityDto != null) {
                            amenities.add(amenityDto);
                        }
                    }
                    dto.setAmenities(amenities);
                    return dto;
                })
                .toList();
    }

    public void deleteHouse(String id) {
        houseRepository.deleteById(UUID.fromString(id));
        houseAmenityRepository.deleteAll(houseAmenityRepository.findByHouseId(UUID.fromString(id)));
    }
}
