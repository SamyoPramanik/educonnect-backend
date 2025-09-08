package com.educonnect.student_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.student_service.dto.ProfessorDetailsDto;
import com.educonnect.student_service.model.FavouriteHouse;
import com.educonnect.student_service.model.FavouriteProfessor;
import com.educonnect.student_service.repository.FavouriteHouseRepository;
import com.educonnect.student_service.repository.FavouriteProfessorRepository;
import com.educonnect.student_service.repository.FavouriteScholarshipRepository;
import com.educonnect.student_service.repository.StudentRepository;
import com.educonnect.student_service.util.EducationUtil;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FavouriteProfessorRepository favouriteProfessorRepository;
    private final FavouriteScholarshipRepository favouriteScholarshipRepository;
    private final FavouriteHouseRepository favouriteHouseRepository;
    private final EducationUtil educationUtil;

    public StudentService(StudentRepository studentRepository,
            FavouriteProfessorRepository favouriteProfessorRepository,
            FavouriteScholarshipRepository favouriteScholarshipRepository,
            FavouriteHouseRepository favouriteHouseRepository, EducationUtil educationUtil) {
        this.studentRepository = studentRepository;
        this.favouriteProfessorRepository = favouriteProfessorRepository;
        this.favouriteScholarshipRepository = favouriteScholarshipRepository;
        this.favouriteHouseRepository = favouriteHouseRepository;
        this.educationUtil = educationUtil;
    }

    public void addProfessorToFavourites(String studentId, String professorId, String authToken) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID professorUUID = UUID.fromString(professorId);

        ProfessorDetailsDto professorDetails = new ProfessorDetailsDto();
        try {
            professorDetails = educationUtil.getProfessorById(professorUUID, authToken);
            if (professorDetails == null) {
                throw new RuntimeException("Professor not found with ID: " + professorId);
            }
            if (isProfessorInFavourites(studentId, professorId)) {
                throw new RuntimeException("Professor already in favourites");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch professor details: " + e.getMessage());
        }

        FavouriteProfessor favouriteProfessor = new FavouriteProfessor();
        favouriteProfessor.setStudentId(studentUUID);
        favouriteProfessor.setProfessorId(professorUUID);
        favouriteProfessorRepository.save(favouriteProfessor);
    }

    public void removeProfessorFromFavourites(String studentId, String professorId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID professorUUID = UUID.fromString(professorId);

        if (!isProfessorInFavourites(studentId, professorId)) {
            throw new RuntimeException("Professor not in favourites");
        }

        var favourites = favouriteProfessorRepository.findByStudentIdAndProfessorId(studentUUID, professorUUID);
        if (favourites.size() > 0) {
            favouriteProfessorRepository.delete(favourites.get(0));
        }
    }

    public void addHouseToFavourites(String studentId, String houseId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID houseUUID = UUID.fromString(houseId);

        if (isHouseInFavourites(studentId, houseId)) {
            throw new RuntimeException("House already in favourites");
        }

        var favouriteHouse = new FavouriteHouse();
        favouriteHouse.setStudentId(studentUUID);
        favouriteHouse.setHouseId(houseUUID);
        favouriteHouseRepository.save(favouriteHouse);
    }

    public void removeHouseFromFavourites(String studentId, String houseId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID houseUUID = UUID.fromString(houseId);

        if (!isHouseInFavourites(studentId, houseId)) {
            throw new RuntimeException("House not in favourites");
        }

        var favourites = favouriteHouseRepository.findByStudentIdAndHouseId(studentUUID, houseUUID);
        if (favourites.size() > 0) {
            favouriteHouseRepository.delete(favourites.get(0));
        }
    }

    public void addScholarshipToFavourites(String studentId, String scholarshipId, String authToken) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID scholarshipUUID = UUID.fromString(scholarshipId);

        try {
            var scholarshipDetails = educationUtil.getScholarshipById(scholarshipUUID, authToken);
            if (scholarshipDetails == null) {
                throw new RuntimeException("Scholarship not found with ID: " + scholarshipId);
            }
            if (isScholarshipInFavourites(studentId, scholarshipId)) {
                throw new RuntimeException("Scholarship already in favourites");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch scholarship details: " + e.getMessage());
        }
        var favouriteScholarship = new com.educonnect.student_service.model.FavouriteScholarship();
        favouriteScholarship.setStudentId(studentUUID);
        favouriteScholarship.setScholarshipId(scholarshipUUID);
        favouriteScholarship.setStatus("Not Applied");
        favouriteScholarshipRepository.save(favouriteScholarship);
    }

    public void removeScholarshipFromFavourites(String studentId, String scholarshipId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID scholarshipUUID = UUID.fromString(scholarshipId);

        if (!isScholarshipInFavourites(studentId, scholarshipId)) {
            throw new RuntimeException("Scholarship not in favourites");
        }

        var favourites = favouriteScholarshipRepository.findByStudentIdAndScholarshipId(studentUUID, scholarshipUUID);
        if (favourites.size() > 0) {
            favouriteScholarshipRepository.delete(favourites.get(0));
        }
    }

    private boolean isProfessorInFavourites(String studentId, String professorId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID professorUUID = UUID.fromString(professorId);
        return favouriteProfessorRepository.findByStudentIdAndProfessorId(studentUUID, professorUUID).size() > 0;
    }

    private boolean isHouseInFavourites(String studentId, String houseId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID houseUUID = UUID.fromString(houseId);
        return favouriteHouseRepository.findByStudentIdAndHouseId(studentUUID, houseUUID).size() > 0;
    }

    private boolean isScholarshipInFavourites(String studentId, String scholarshipId) {
        UUID studentUUID = UUID.fromString(studentId);
        UUID scholarshipUUID = UUID.fromString(scholarshipId);
        return favouriteScholarshipRepository.findByStudentIdAndScholarshipId(studentUUID, scholarshipUUID).size() > 0;
    }
}
