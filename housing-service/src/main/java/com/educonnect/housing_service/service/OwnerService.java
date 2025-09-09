package com.educonnect.housing_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.housing_service.dto.OwnerDto;
import com.educonnect.housing_service.model.Owner;
import com.educonnect.housing_service.repository.OwnerRepository;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerDto createOwner(OwnerDto ownerDto) {
        UUID userId = UUID.fromString(ownerDto.getUserId());
        Owner existingOwner = ownerRepository.findByUserId(userId);
        if (existingOwner != null) {
            throw new RuntimeException("Owner with this userId already exists");
        }
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setEmail(ownerDto.getEmail());
        owner.setPhone(ownerDto.getPhone());
        owner.setAddress(ownerDto.getAddress());
        owner.setUserId(userId);
        Owner savedOwner = ownerRepository.save(owner);
        ownerDto.setId(savedOwner.getId().toString());
        return ownerDto;
    }

    public OwnerDto updateOwner(String id, OwnerDto ownerDto) {
        return ownerRepository.findById(UUID.fromString(id))
                .map(owner -> {
                    owner.setName(ownerDto.getName());
                    owner.setEmail(ownerDto.getEmail());
                    owner.setPhone(ownerDto.getPhone());
                    owner.setAddress(ownerDto.getAddress());
                    owner.setUserId(UUID.fromString(ownerDto.getId()));
                    var updatedOwner = ownerRepository.save(owner);
                    ownerDto.setId(updatedOwner.getId().toString());
                    return ownerDto;
                })
                .orElse(null);
    }

    // public void deleteOwner(String id) {
    // ownerRepository.deleteById(UUID.fromString(id));
    // }

    public OwnerDto getOwnerById(String id) {
        return ownerRepository.findById(UUID.fromString(id))
                .map(owner -> {
                    OwnerDto dto = new OwnerDto();
                    dto.setId(owner.getId().toString());
                    dto.setName(owner.getName());
                    dto.setEmail(owner.getEmail());
                    dto.setPhone(owner.getPhone());
                    dto.setAddress(owner.getAddress());
                    dto.setUserId(owner.getUserId().toString());
                    return dto;
                })
                .orElse(null);
    }

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream().map(owner -> {
            OwnerDto dto = new OwnerDto();
            dto.setId(owner.getId().toString());
            dto.setName(owner.getName());
            dto.setEmail(owner.getEmail());
            dto.setPhone(owner.getPhone());
            dto.setAddress(owner.getAddress());
            dto.setUserId(owner.getUserId().toString());
            return dto;
        }).toList();
    }

    public OwnerDto getOwnerByUserId(String userId) {
        Owner owner = ownerRepository.findByUserId(UUID.fromString(userId));
        if (owner != null) {
            OwnerDto dto = new OwnerDto();
            dto.setId(owner.getId().toString());
            dto.setName(owner.getName());
            dto.setEmail(owner.getEmail());
            dto.setPhone(owner.getPhone());
            dto.setAddress(owner.getAddress());
            dto.setUserId(owner.getUserId().toString());
            return dto;
        }
        throw new RuntimeException("Owner not found");
    }
}
