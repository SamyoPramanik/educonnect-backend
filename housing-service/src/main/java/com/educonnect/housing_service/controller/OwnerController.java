package com.educonnect.housing_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.housing_service.dto.OwnerDto;
import com.educonnect.housing_service.dto.UserDto;
import com.educonnect.housing_service.service.OwnerService;
import com.educonnect.housing_service.util.UserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final UserUtil userUtil;

    public OwnerController(OwnerService ownerService, UserUtil userUtil) {
        this.ownerService = ownerService;
        this.userUtil = userUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<OwnerDto> createOwner(@RequestHeader("Authorization") String token,
            @Valid @RequestBody OwnerDto ownerDto) {
        UserDto userDto = userUtil.getUser(token);
        String role = userDto.getRole();
        String userId = userDto.getId();
        if (!role.equals("ADMIN") && !role.equals("MODERATOR") && !role.equals("HOME_OWNER")) {
            throw new RuntimeException("Unauthorized");
        }
        ownerDto.setUserId(userId);
        System.out.println("Owner name: " + ownerDto.getName());
        OwnerDto createdOwner = ownerService.createOwner(ownerDto);
        return ResponseEntity.ok(createdOwner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestHeader("Authorization") String token, @PathVariable String id,
            @Valid @RequestBody OwnerDto ownerDto) {
        UserDto userDto = userUtil.getUser(token);
        String role = userDto.getRole();
        String userId = userDto.getId();
        if (!role.equals("ADMIN") && !role.equals("MODERATOR") && !role.equals("HOME_OWNER")) {
            throw new RuntimeException("Unauthorized");
        }
        ownerDto.setUserId(userId);
        OwnerDto updatedOwner = ownerService.updateOwner(id, ownerDto);
        if (updatedOwner != null) {
            return ResponseEntity.ok(updatedOwner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        List<OwnerDto> ownerDtos = ownerService.getAllOwners();
        return ResponseEntity.ok(ownerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable String id) {
        OwnerDto ownerDto = ownerService.getOwnerById(id);
        if (ownerDto != null) {
            return ResponseEntity.ok(ownerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<OwnerDto> getOwnerByUserId(@PathVariable String userId) {
        OwnerDto ownerDto = ownerService.getOwnerById(userId);
        if (ownerDto != null) {
            return ResponseEntity.ok(ownerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
