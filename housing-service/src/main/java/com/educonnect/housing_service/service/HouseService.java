package com.educonnect.housing_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.housing_service.dto.AmenityDto;
import com.educonnect.housing_service.dto.HouseDto;
import com.educonnect.housing_service.dto.HouseEditDto;
import com.educonnect.housing_service.dto.OwnerDto;
import com.educonnect.housing_service.model.House;
import com.educonnect.housing_service.model.HouseAmenity;
import com.educonnect.housing_service.repository.HouseAmenityRepository;
import com.educonnect.housing_service.repository.HouseRepository;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    private final AmenityService amenityService;
    private final HouseAmenityRepository houseAmenityRepository;
    private final OwnerService ownerService;

    public HouseService(HouseRepository houseRepository, AmenityService amenityService,
            HouseAmenityRepository houseAmenityRepository, OwnerService ownerService) {
        this.houseRepository = houseRepository;
        this.amenityService = amenityService;
        this.houseAmenityRepository = houseAmenityRepository;
        this.ownerService = ownerService;
    }

    public HouseEditDto createHouse(HouseEditDto houseEditDto) {
        // Convert DTO to entity
        var houseModel = new House();
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
        houseModel.setOwnerId(UUID.fromString(houseEditDto.getOwnerId()));
        houseModel.setUniversityId(UUID.fromString(houseEditDto.getUniversityId()));
        houseModel.setDistanceToUniversity(Double.parseDouble(houseEditDto.getDistanceToUniversity()));

        for (AmenityDto amenity : houseEditDto.getAmenities()) {
            AmenityDto amenityDto = amenityService.getAmenityById(UUID.fromString(amenity.getId()).toString());
            if (amenityDto != null) {
                HouseAmenity houseAmenity = new HouseAmenity();
                houseAmenity.setAmenityId(UUID.fromString(amenityDto.getId()));
                houseAmenity.setHouseId(UUID.fromString(houseEditDto.getId()));
                houseAmenityRepository.save(houseAmenity);
            }
        }

        // Save entity
        var savedHouse = houseRepository.save(houseModel);

        // Convert entity back to DTO
        houseEditDto.setId(savedHouse.getId().toString());

        return houseEditDto;
    }

    public HouseDto getHouseById(String id) {
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
                    dto.setOwner(ownerDto.getName());
                    dto.setContactEmail(ownerDto.getEmail());
                    dto.setContactPhone(ownerDto.getPhone());
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
        return houseRepository.findById(UUID.fromString(id))
                .map(house -> {
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
                    house.setOwnerId(UUID.fromString(houseEditDto.getOwnerId()));
                    house.setUniversityId(UUID.fromString(houseEditDto.getUniversityId()));
                    house.setDistanceToUniversity(Double.parseDouble(houseEditDto.getDistanceToUniversity()));

                    // Update amenities
                    var existingAmenities = houseAmenityRepository.findByHouseId(house.getId());
                    houseAmenityRepository.deleteAll(existingAmenities);

                    for (AmenityDto amenityDto : houseEditDto.getAmenities()) {
                        AmenityDto amenityDto2 = amenityService
                                .getAmenityById(UUID.fromString(amenityDto.getId()).toString());
                        if (amenityDto2 != null) {
                            HouseAmenity houseAmenity = new HouseAmenity();
                            houseAmenity.setAmenityId(UUID.fromString(amenityDto2.getId()));
                            houseAmenity.setHouseId(house.getId());
                            houseAmenityRepository.save(houseAmenity);
                        }
                    }

                    var updatedHouse = houseRepository.save(house);
                    houseEditDto.setId(updatedHouse.getId().toString());
                    return houseEditDto;
                })
                .orElse(null);
    }

    public List<HouseDto> getAllHouses() {
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

                    OwnerDto ownerDto = ownerService.getOwnerById(house.getOwnerId().toString());
                    dto.setOwner(ownerDto.getName());
                    dto.setContactEmail(ownerDto.getEmail());
                    dto.setContactPhone(ownerDto.getPhone());

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
