package com.scholartrack.service;

import com.scholartrack.dto.SchemeDto;
import com.scholartrack.entity.Scheme;
import com.scholartrack.exception.ResourceNotFoundException;
import com.scholartrack.repository.SchemeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;

    public SchemeService(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    public SchemeDto createScheme(@Valid SchemeDto dto) {
        Scheme scheme = mapToEntity(dto);
        return mapToDto(schemeRepository.save(scheme));
    }

    public List<SchemeDto> getAllSchemes() {
        return schemeRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public SchemeDto getSchemeById(Long id) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with id: " + id));
        return mapToDto(scheme);
    }

    public SchemeDto updateScheme(Long id, @Valid SchemeDto dto) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with id: " + id));

        scheme.setSchemeName(dto.getSchemeName());
        scheme.setDescription(dto.getDescription());
        scheme.setProvider(dto.getProvider());
        scheme.setIncomeLimit(dto.getIncomeLimit());
        scheme.setMinimumMarks(dto.getMinimumMarks());
        scheme.setEligibleCategories(dto.getEligibleCategories());
        scheme.setRequiredDocuments(dto.getRequiredDocuments());
        scheme.setApplicationDeadline(dto.getApplicationDeadline());
        scheme.setScholarshipAmount(dto.getScholarshipAmount());
        scheme.setActive(dto.isActive());

        return mapToDto(schemeRepository.save(scheme));
    }

    public void deleteScheme(Long id) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with id: " + id));
        schemeRepository.delete(scheme);
    }

    private Scheme mapToEntity(SchemeDto dto) {
        Scheme scheme = new Scheme();
        scheme.setId(dto.getId());
        scheme.setSchemeName(dto.getSchemeName());
        scheme.setDescription(dto.getDescription());
        scheme.setProvider(dto.getProvider());
        scheme.setIncomeLimit(dto.getIncomeLimit());
        scheme.setMinimumMarks(dto.getMinimumMarks());
        scheme.setEligibleCategories(dto.getEligibleCategories());
        scheme.setRequiredDocuments(dto.getRequiredDocuments());
        scheme.setApplicationDeadline(dto.getApplicationDeadline());
        scheme.setScholarshipAmount(dto.getScholarshipAmount());
        scheme.setActive(dto.isActive());
        return scheme;
    }

    private SchemeDto mapToDto(Scheme scheme) {
        SchemeDto dto = new SchemeDto();
        dto.setId(scheme.getId());
        dto.setSchemeName(scheme.getSchemeName());
        dto.setDescription(scheme.getDescription());
        dto.setProvider(scheme.getProvider());
        dto.setIncomeLimit(scheme.getIncomeLimit());
        dto.setMinimumMarks(scheme.getMinimumMarks());
        dto.setEligibleCategories(scheme.getEligibleCategories());
        dto.setRequiredDocuments(scheme.getRequiredDocuments());
        dto.setApplicationDeadline(scheme.getApplicationDeadline());
        dto.setScholarshipAmount(scheme.getScholarshipAmount());
        dto.setActive(scheme.isActive());
        return dto;
    }
}
