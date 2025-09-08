package com.educonnect.education_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.educonnect.education_service.dto.ExperienceDto;
import com.educonnect.education_service.dto.FieldDto;
import com.educonnect.education_service.dto.PaperDto;
import com.educonnect.education_service.dto.ProfessorDetailsDto;
import com.educonnect.education_service.dto.ProfessorDto;
import com.educonnect.education_service.dto.ProfessorShortDetailsDto;
import com.educonnect.education_service.repository.ExperienceRepository;
import com.educonnect.education_service.repository.FieldRepository;
import com.educonnect.education_service.repository.PaperRepository;
import com.educonnect.education_service.repository.ProfessorFieldRepository;
import com.educonnect.education_service.repository.ProfessorRepository;
import com.educonnect.education_service.repository.UniversityRepository;
import com.educonnect.education_service.model.Experience;
import com.educonnect.education_service.model.Paper;
import com.educonnect.education_service.model.Professor;
import com.educonnect.education_service.model.ProfessorField;
import com.educonnect.education_service.model.University;
import com.educonnect.education_service.model.Field;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final FieldRepository fieldRepository;
    private final ExperienceRepository experienceRepository;
    private final UniversityRepository universityRepository;
    private final PaperRepository paperRepository;
    private final ProfessorFieldRepository professorFieldRepository;

    public ProfessorService(ProfessorRepository professorRepository, FieldRepository fieldRepository,
            ExperienceRepository experienceRepository, UniversityRepository universityRepository,
            PaperRepository paperRepository, ProfessorFieldRepository professorFieldRepository) {
        this.professorRepository = professorRepository;
        this.fieldRepository = fieldRepository;
        this.experienceRepository = experienceRepository;
        this.universityRepository = universityRepository;
        this.paperRepository = paperRepository;
        this.professorFieldRepository = professorFieldRepository;
    }

    public List<ProfessorShortDetailsDto> getProfessors() {
        List<ProfessorShortDetailsDto> professorShortDetailsDtos = new ArrayList<>();
        professorRepository.findAll().forEach(professor -> {
            ProfessorShortDetailsDto dto = new ProfessorShortDetailsDto();
            dto.setId(professor.getId().toString());
            dto.setName(professor.getName());
            dto.setBio(professor.getBio());
            dto.setCountry(professor.getCountry());
            professorShortDetailsDtos.add(dto);

            Optional<UUID> universityId = experienceRepository.findCurrentUniversityIdByProfessorId(professor.getId());
            if (universityId.isPresent()) {
                String universityName = getUniversityName(universityId.get());
                dto.setUniversity(universityName);
            }
            dto.setPublicationCount(String.valueOf(getPublicationsCount(professor.getId())));
        });
        return professorShortDetailsDtos;
    }

    public ProfessorDetailsDto getProfessor(String professorId) {
        Professor professor = professorRepository.findById(UUID.fromString(professorId)).orElse(null);
        if (professor == null) {
            return null;
        }
        ProfessorDetailsDto professorDetailsDto = new ProfessorDetailsDto();
        professorDetailsDto.setId(professor.getId().toString());
        professorDetailsDto.setName(professor.getName());
        professorDetailsDto.setBio(professor.getBio());
        professorDetailsDto.setCountry(professor.getCountry());
        professorDetailsDto.setEmail(professor.getEmail());
        professorDetailsDto.setUrl(professor.getUrl());

        List<ExperienceDto> experiencesDetails = new ArrayList<>();
        List<Experience> experiences = experienceRepository.findByProfessorId(professor.getId());
        for (Experience experience : experiences) {
            ExperienceDto expDto = new ExperienceDto();
            expDto.setId(experience.getId().toString());
            expDto.setUniversityId(experience.getUniversityId().toString());
            expDto.setTitle(experience.getTitle());
            expDto.setStartYear(String.valueOf(experience.getStartYear()));
            expDto.setEndYear(String.valueOf(experience.getEndYear()));
            String universityName = getUniversityName(experience.getUniversityId());
            expDto.setUniversityName(universityName);
            experiencesDetails.add(expDto);
        }
        professorDetailsDto.setExperiences(experiencesDetails);

        List<PaperDto> paperDtos = new ArrayList<>();
        List<Paper> papers = paperRepository.findByProfessorId(professor.getId());
        for (Paper paper : papers) {
            PaperDto paperDto = new PaperDto();
            paperDto.setId(paper.getId().toString());
            paperDto.setTitle(paper.getTitle());
            paperDto.setAbstractText(paper.getAbstractText());
            paperDto.setJournal(paper.getJournal());
            paperDto.setYear(String.valueOf(paper.getYear()));
            paperDtos.add(paperDto);
        }
        professorDetailsDto.setPapers(paperDtos);

        List<ProfessorField> fieldIds = professorFieldRepository.findByProfessorId(professor.getId());
        List<FieldDto> fields = new ArrayList<>();
        for (ProfessorField field : fieldIds) {
            FieldDto fieldDto = new FieldDto();
            Field f = fieldRepository.findById(field.getFieldId()).orElse(null);
            if (f != null) {
                fieldDto.setId(f.getId().toString());
                fieldDto.setTitle(f.getTitle());
                fields.add(fieldDto);
            }
            fields.add(fieldDto);
        }
        professorDetailsDto.setFields(fields);

        return professorDetailsDto;
    }

    public ProfessorDto createProfessor(ProfessorDto professorDto) {
        Professor professor = new Professor(professorDto.getName(), professorDto.getBio(), professorDto.getCountry(),
                professorDto.getEmail(), professorDto.getUrl());
        Professor savedProfessor = professorRepository.save(professor);
        professorDto.setId(savedProfessor.getId().toString());
        return professorDto;
    }

    public ProfessorDto updateProfessor(String professorId, ProfessorDto professorDto) {
        Professor professor = professorRepository.findById(UUID.fromString(professorId)).orElse(null);
        if (professor != null) {
            professor.setName(professorDto.getName());
            professor.setBio(professorDto.getBio());
            professor.setCountry(professorDto.getCountry());
            professor.setEmail(professorDto.getEmail());
            professor.setUrl(professorDto.getUrl());
            professorRepository.save(professor);
        }
        return professorDto;
    }

    public void addExperience(String professorId, ExperienceDto experienceDto) {
        UUID profId = UUID.fromString(professorId);
        Professor professor = professorRepository.findById(profId).orElse(null);
        if (professor != null) {
            Experience experience = new Experience();
            experience.setUniversityId(UUID.fromString(experienceDto.getUniversityId()));
            experience.setProfessorId(profId);
            experience.setTitle(experienceDto.getTitle());
            experience.setStartYear(Integer.parseInt(experienceDto.getStartYear()));
            experience.setEndYear(Integer.parseInt(experienceDto.getEndYear()));
            experienceRepository.save(experience);
        }
    }

    public void deleteExperience(String professorId, String experienceId) {
        UUID profId = UUID.fromString(professorId);
        UUID expId = UUID.fromString(experienceId);
        Experience experience = experienceRepository.findById(expId).orElse(null);
        if (experience != null && experience.getProfessorId().equals(profId)) {
            experienceRepository.delete(experience);
        }
    }

    public void addPaper(String professorId, PaperDto paperDto) {
        UUID profId = UUID.fromString(professorId);
        Professor professor = professorRepository.findById(profId).orElse(null);
        if (professor != null) {
            Paper paper = new Paper();
            paper.setProfessorId(profId);
            paper.setTitle(paperDto.getTitle());
            paper.setAbstractText(paperDto.getAbstractText());
            paper.setYear(Integer.parseInt(paperDto.getYear()));
            paper.setJournal(paperDto.getJournal());
            paperRepository.save(paper);
        }
    }

    public void deletePaper(String professorId, String paperId) {
        UUID profId = UUID.fromString(professorId);
        UUID pId = UUID.fromString(paperId);
        Paper paper = paperRepository.findById(pId).orElse(null);
        if (paper != null && paper.getProfessorId().equals(profId)) {
            paperRepository.delete(paper);
        }
    }

    public void addField(String professorId, String fieldId) {
        UUID profId = UUID.fromString(professorId);
        Professor professor = professorRepository.findById(profId).orElse(null);
        if (professor != null) {
            UUID fId = UUID.fromString(fieldId);
            Field field = fieldRepository.findById(fId).orElse(null);
            if (field != null) {
                ProfessorField existing = professorFieldRepository.findByProfessorIdAndFieldId(profId, fId);
                if (existing != null) {
                    return;
                }
                ProfessorField professorField = new ProfessorField();
                professorField.setProfessorId(profId);
                professorField.setFieldId(fId);
                professorFieldRepository.save(professorField);
            }
        }
    }

    public void deleteField(String professorId, String fieldId) {
        UUID profId = UUID.fromString(professorId);
        UUID fId = UUID.fromString(fieldId);
        ProfessorField professorField = professorFieldRepository.findByProfessorIdAndFieldId(profId, fId);
        if (professorField != null) {
            professorFieldRepository.delete(professorField);
        }
    }

    private String getUniversityName(UUID universityId) {
        Optional<University> university = universityRepository.findById(universityId);
        return university.isPresent() ? university.get().getName() : "Unknown University";
    }

    private int getPublicationsCount(UUID professorId) {
        return paperRepository.countByProfessorId(professorId);
    }

}
