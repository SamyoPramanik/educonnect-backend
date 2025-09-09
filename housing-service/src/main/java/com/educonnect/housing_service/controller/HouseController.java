package com.educonnect.housing_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.housing_service.dto.HouseDto;
import com.educonnect.housing_service.dto.HouseEditDto;
import com.educonnect.housing_service.dto.OwnerDto;
import com.educonnect.housing_service.dto.UserDto;
import com.educonnect.housing_service.model.Owner;
import com.educonnect.housing_service.service.HouseService;
import com.educonnect.housing_service.service.OwnerService;
import com.educonnect.housing_service.util.UserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseService houseService;
    private final UserUtil userUtil;
    private final OwnerService ownerService;

    public HouseController(HouseService houseService, UserUtil userUtil, OwnerService ownerService) {
        this.houseService = houseService;
        this.userUtil = userUtil;
        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    public ResponseEntity<HouseEditDto> createHouse(@RequestHeader("Authorization") String token,
            @Valid @RequestBody HouseEditDto houseDto) {
        UserDto userDto = userUtil.getUser(token);
        String userId = userDto.getId();
        String role = userDto.getRole();
        if (!role.equals("ADMIN") && !role.equals("MODERATOR") && !role.equals("HOME_OWNER")) {
            throw new RuntimeException("Unauthorized");
        }
        OwnerDto owner = ownerService.getOwnerByUserId(userId);
        if (owner == null) {
            throw new RuntimeException("Owner not found for user id: " + userId);
        }
        houseDto.setOwnerId(owner.getId());
        HouseEditDto createdHouse = houseService.createHouse(houseDto);
        return ResponseEntity.ok(createdHouse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> getHouseById(@RequestHeader("Authorization") String token,
            @PathVariable String id) {
        UserDto userDto = userUtil.getUser(token);
        HouseDto houseDto = houseService.getHouseById(id, token);
        if (houseDto != null) {
            return ResponseEntity.ok(houseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<HouseDto>> getAllHouses(@RequestHeader("Authorization") String token) {
        UserDto userDto = userUtil.getUser(token);
        List<HouseDto> houseDtos = houseService.getAllHouses(token);
        return ResponseEntity.ok(houseDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseEditDto> updateHouse(@RequestHeader("Authorization") String token,
            @PathVariable String id, @RequestBody HouseEditDto houseDto) {
        UserDto userDto = userUtil.getUser(token);
        String role = userDto.getRole();
        String userId = userDto.getId();
        if (!role.equals("ADMIN") && !role.equals("MODERATOR") && !role.equals("HOME_OWNER")) {
            throw new RuntimeException("Unauthorized");
        }
        OwnerDto owner = ownerService.getOwnerByUserId(userId);
        if (owner == null) {
            throw new RuntimeException("Owner not found for user id: " + userId);
        }
        houseDto.setOwnerId(owner.getId());
        HouseEditDto updatedHouse = houseService.updateHouse(id, houseDto);
        if (updatedHouse != null) {
            return ResponseEntity.ok(updatedHouse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(String id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }

}
