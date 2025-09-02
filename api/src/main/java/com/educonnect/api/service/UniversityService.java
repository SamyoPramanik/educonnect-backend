package com.educonnect.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educonnect.api.dto.UniversityDto;
import com.educonnect.api.model.University;
import com.educonnect.api.repository.UniversityRepository;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public UniversityDto createUniversity(UniversityDto universityDto) {
        Optional<University> existingUniversity = universityRepository.findByName(universityDto.getName());
        System.out.println("Creating university from service: " + universityDto.getName());
        if (existingUniversity.isPresent()) {
            throw new IllegalArgumentException("University with name " + universityDto.getName() + " already exists");
        }
        University university = new University();
        university.setName(universityDto.getName());
        university.setCountry(universityDto.getCountry());
        university.setState(universityDto.getState());
        University newUniversity = universityRepository.save(university);
        return new UniversityDto(newUniversity.getId().toString(), newUniversity.getName(), newUniversity.getCountry(),
                newUniversity.getState());
    }

    public Optional<UniversityDto> getUniversityById(UUID id) {
        Optional<University> universityOptional = universityRepository.findById(id);
        if (universityOptional.isPresent()) {
            University university = universityOptional.get();
            UniversityDto universityDto = new UniversityDto(university.getId().toString(), university.getName(),
                    university.getCountry(), university.getState());
            return Optional.of(universityDto);
        }
        return Optional.empty();
    }

    public void deleteUniversity(UUID id) {
        if (!universityRepository.existsById(id)) {
            throw new IllegalArgumentException("University with id " + id + " does not exist");
        }
        universityRepository.deleteById(id);
    }

    public UniversityDto updateUniversity(UUID id, UniversityDto universityDto) {
        System.out.println("university name: " + universityDto.getName());
        Optional<University> existingUniversity = universityRepository.findByNameAndIdNot(universityDto.getName(), id);
        if (existingUniversity.isPresent()) {
            throw new IllegalArgumentException("University with name " + universityDto.getName() + " already exists");
        }
        existingUniversity = universityRepository.findById(id);
        if (existingUniversity.isPresent()) {
            University university = existingUniversity.get();
            university.setName(universityDto.getName());
            university.setCountry(universityDto.getCountry());
            university.setState(universityDto.getState());
            University updatedUniversity = universityRepository.save(university);
            return new UniversityDto(updatedUniversity.getId().toString(), updatedUniversity.getName(),
                    updatedUniversity.getCountry(), updatedUniversity.getState());
        } else {
            throw new IllegalArgumentException("University with id " + id + " does not exist");
        }
    }

    public List<UniversityDto> getAllUniversities() {
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .map(university -> new UniversityDto(university.getId().toString(), university.getName(),
                        university.getCountry(), university.getState()))
                .collect(Collectors.toList());
    }

    public List<UniversityDto> searchUniversitiesByName(String name) {
        List<University> universities = universityRepository.findByNameContainingIgnoreCase(name);
        return universities.stream()
                .map(university -> new UniversityDto(university.getId().toString(), university.getName(),
                        university.getCountry(), university.getState()))
                .collect(Collectors.toList());
    }

}
