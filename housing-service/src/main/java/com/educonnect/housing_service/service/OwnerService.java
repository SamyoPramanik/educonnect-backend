package com.educonnect.housing_service.service;

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
        var owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setEmail(ownerDto.getEmail());
        owner.setPhone(ownerDto.getPhone());
        var savedOwner = ownerRepository.save(owner);
        ownerDto.setId(savedOwner.getId().toString());
        return ownerDto;
    }

    public OwnerDto updateOwner(String id, OwnerDto ownerDto) {
        return ownerRepository.findById(UUID.fromString(id))
                .map(owner -> {
                    owner.setName(ownerDto.getName());
                    owner.setEmail(ownerDto.getEmail());
                    owner.setPhone(ownerDto.getPhone());
                    var updatedOwner = ownerRepository.save(owner);
                    ownerDto.setId(updatedOwner.getId().toString());
                    return ownerDto;
                })
                .orElse(null);
    }

    public void deleteOwner(String id) {
        ownerRepository.deleteById(UUID.fromString(id));
    }

    public OwnerDto getOwnerById(String id) {
        return ownerRepository.findById(UUID.fromString(id))
                .map(owner -> {
                    OwnerDto dto = new OwnerDto();
                    dto.setId(owner.getId().toString());
                    dto.setName(owner.getName());
                    dto.setEmail(owner.getEmail());
                    dto.setPhone(owner.getPhone());
                    return dto;
                })
                .orElse(null);
    }
}
