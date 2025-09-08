package com.educonnect.student_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.student_service.dto.UserDto;
import com.educonnect.student_service.service.StudentService;
import com.educonnect.student_service.util.UserUtil;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private final StudentService studentService;
    private final UserUtil userUtil;

    public StudentController(StudentService studentService, UserUtil userUtil) {
        this.studentService = studentService;
        this.userUtil = userUtil;
    }

    @GetMapping("/addProfessorToFavourites/{professorId}")
    public ResponseEntity<String> addProfessorToFavourites(@RequestParam String professorId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);

        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }

        String studentId = userDto.getId();
        studentService.addProfessorToFavourites(studentId, professorId, authToken);
        return ResponseEntity.ok("Professor added to favourites successfully");
    }

    @GetMapping("/removeProfessorFromFavourites/{professorId}")
    public ResponseEntity<String> removeProfessorFromFavourites(@RequestParam String professorId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);
        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }
        String studentId = userDto.getId();
        studentService.removeProfessorFromFavourites(studentId, professorId);
        return ResponseEntity.ok("Professor removed from favourites successfully");
    }

    @GetMapping("/addScholarshipToFavourites/{scholarshipId}")
    public ResponseEntity<String> addScholarshipToFavourites(@RequestParam String scholarshipId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);
        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }
        String studentId = userDto.getId();
        studentService.addScholarshipToFavourites(studentId, scholarshipId, authToken);
        return ResponseEntity.ok("Scholarship added to favourites successfully");
    }

    @GetMapping("/removeScholarshipFromFavourites/{scholarshipId}")
    public ResponseEntity<String> removeScholarshipFromFavourites(@RequestParam String scholarshipId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);
        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }
        String studentId = userDto.getId();
        studentService.removeScholarshipFromFavourites(studentId, scholarshipId);
        return ResponseEntity.ok("Scholarship removed from favourites successfully");
    }

    @GetMapping("/addHouseToFavourites/{houseId}")
    public ResponseEntity<String> addHouseToFavourites(@RequestParam String houseId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);
        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }
        String studentId = userDto.getId();
        studentService.addHouseToFavourites(studentId, houseId);
        return ResponseEntity.ok("House added to favourites successfully");
    }

    @GetMapping("/removeHouseFromFavourites/{houseId}")
    public ResponseEntity<String> removeHouseFromFavourites(@RequestParam String houseId,
            @RequestHeader("Authorization") String authToken) {
        UserDto userDto = userUtil.getUser(authToken);
        if (userDto == null || userDto.getRole() != "STUDENT" || userDto.getId() != "MODERATOR"
                || userDto.getId() != "ADMIN") {
            return ResponseEntity.status(403).body("Access denied");
        }
        String studentId = userDto.getId();
        studentService.removeHouseFromFavourites(studentId, houseId);
        return ResponseEntity.ok("House removed from favourites successfully");
    }

}
