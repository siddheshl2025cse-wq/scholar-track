package com.scholartrack.service;

import com.scholartrack.dto.ApplicationRequestDto;
import com.scholartrack.dto.ApplicationResponseDto;
import com.scholartrack.entity.Application;
import com.scholartrack.entity.Scheme;
import com.scholartrack.entity.Student;
import com.scholartrack.entity.Verification;
import com.scholartrack.enums.ApplicationStatus;
import com.scholartrack.enums.DisbursementStatus;
import com.scholartrack.enums.EligibilityStatus;
import com.scholartrack.enums.VerificationStatus;
import com.scholartrack.exception.DisbursementException;
import com.scholartrack.exception.EligibilityException;
import com.scholartrack.exception.InvalidApplicationStateException;
import com.scholartrack.exception.ResourceNotFoundException;
import com.scholartrack.repository.ApplicationRepository;
import com.scholartrack.repository.DisbursementRepository;
import com.scholartrack.repository.SchemeRepository;
import com.scholartrack.repository.StudentRepository;
import com.scholartrack.repository.VerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final SchemeRepository schemeRepository;
    private final VerificationRepository verificationRepository;
    private final DisbursementRepository disbursementRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                             StudentRepository studentRepository,
                             SchemeRepository schemeRepository,
                             VerificationRepository verificationRepository,
                             DisbursementRepository disbursementRepository) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.schemeRepository = schemeRepository;
        this.verificationRepository = verificationRepository;
        this.disbursementRepository = disbursementRepository;
    }

    @Transactional
    public ApplicationResponseDto submitApplication(ApplicationRequestDto request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));
        Scheme scheme = schemeRepository.findById(request.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with id: " + request.getSchemeId()));

        Application application = new Application();
        application.setStudent(student);
        application.setScheme(scheme);
        application.setApplicationDate(LocalDate.now());
        application.setApplicationNumber(generateApplicationNumber());
        application.setSubmitted(true);

        EligibilityStatus eligibilityResult = calculateEligibility(student, scheme);
        application.setEligibilityStatus(eligibilityResult);

        if (eligibilityResult == EligibilityStatus.INELIGIBLE) {
            application.setApplicationStatus(ApplicationStatus.INELIGIBLE);
            application.setRemarks("Application is not eligible for this scholarship scheme.");
            throw new EligibilityException("Application is not eligible for this scholarship scheme.");
        }

        application.setApplicationStatus(ApplicationStatus.PENDING_VERIFICATION);
        application.setRemarks("Eligible application submitted for verification.");

        Application saved = applicationRepository.save(application);
        return mapToResponse(saved);
    }

    public List<ApplicationResponseDto> getAllApplications() {
        return applicationRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ApplicationResponseDto getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return mapToResponse(application);
    }

    public List<ApplicationResponseDto> getApplicationsByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        return applicationRepository.findByStudentId(student.getId()).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ApplicationResponseDto getApplicationStatus(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return mapToResponse(application);
    }

    public List<ApplicationResponseDto> getPendingVerifications() {
        return applicationRepository.findByApplicationStatus(ApplicationStatus.PENDING_VERIFICATION)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public ApplicationResponseDto approveApplication(Long applicationId, String verifierName, String remarks) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (application.getEligibilityStatus() != EligibilityStatus.ELIGIBLE) {
            throw new InvalidApplicationStateException("Only eligible applications can be approved.");
        }
        if (application.getApplicationStatus() == ApplicationStatus.REJECTED || application.getApplicationStatus() == ApplicationStatus.DISBURSED) {
            throw new InvalidApplicationStateException("This application cannot be approved in its current state.");
        }

        application.setApplicationStatus(ApplicationStatus.APPROVED);
        application.setRemarks(remarks != null && !remarks.trim().isEmpty() ? remarks : "Approved after verification.");

        Verification verification = verificationRepository.findByApplication(application)
                .orElseGet(Verification::new);
        verification.setApplication(application);
        verification.setVerifierName(verifierName == null || verifierName.isBlank() ? "Verifier" : verifierName);
        verification.setVerificationStatus(VerificationStatus.APPROVED);
        verification.setRemarks(application.getRemarks());
        verification.setVerifiedAt(java.time.LocalDateTime.now());
        verification.setDocumentStatus(com.scholartrack.enums.DocumentStatus.VERIFIED);
        verificationRepository.save(verification);

        applicationRepository.save(application);
        return mapToResponse(application);
    }

    @Transactional
    public ApplicationResponseDto rejectApplication(Long applicationId, String verifierName, String remarks) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (remarks == null || remarks.trim().isEmpty()) {
            throw new InvalidApplicationStateException("Verifier must provide remarks when rejecting an application.");
        }

        if (application.getApplicationStatus() == ApplicationStatus.DISBURSED) {
            throw new InvalidApplicationStateException("A disbursed application cannot be rejected.");
        }

        application.setApplicationStatus(ApplicationStatus.REJECTED);
        application.setRemarks(remarks);

        Verification verification = verificationRepository.findByApplication(application)
                .orElseGet(Verification::new);
        verification.setApplication(application);
        verification.setVerifierName(verifierName == null || verifierName.isBlank() ? "Verifier" : verifierName);
        verification.setVerificationStatus(VerificationStatus.REJECTED);
        verification.setRemarks(remarks);
        verification.setVerifiedAt(java.time.LocalDateTime.now());
        verification.setDocumentStatus(com.scholartrack.enums.DocumentStatus.REJECTED);
        verificationRepository.save(verification);

        applicationRepository.save(application);
        return mapToResponse(application);
    }

    public List<ApplicationResponseDto> getPendingDisbursementApplications() {
        return applicationRepository.findByApplicationStatus(ApplicationStatus.DISBURSEMENT_PENDING)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public ApplicationResponseDto processDisbursement(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (application.getEligibilityStatus() != EligibilityStatus.ELIGIBLE) {
            throw new DisbursementException("Disbursement cannot be completed until verification is approved.");
        }

        Verification verification = verificationRepository.findByApplication(application)
                .orElse(null);
        if (verification == null || verification.getVerificationStatus() != VerificationStatus.APPROVED) {
            throw new DisbursementException("Disbursement cannot be completed until verification is approved.");
        }

        if (application.getApplicationStatus() == ApplicationStatus.REJECTED) {
            throw new DisbursementException("A rejected application cannot be marked as disbursed.");
        }

        application.setApplicationStatus(ApplicationStatus.DISBURSEMENT_PENDING);
        if (application.getDisbursement() == null) {
            com.scholartrack.entity.Disbursement disbursement = new com.scholartrack.entity.Disbursement();
            disbursement.setApplication(application);
            disbursement.setAmount(application.getScheme().getScholarshipAmount());
            disbursement.setDisbursementStatus(DisbursementStatus.PENDING);
            disbursement.setTransactionReference("ST-" + LocalDate.now().getYear() + "-" + String.format("%06d", (int) (Math.random() * 1000000)));
            disbursement.setDisbursementDate(LocalDate.now());
            disbursement.setRemarks("Disbursement in progress");
            disbursementRepository.save(disbursement);
        }

        application.setApplicationStatus(ApplicationStatus.DISBURSED);
        application.setRemarks("Disbursed successfully.");

        com.scholartrack.entity.Disbursement disbursement = application.getDisbursement();
        if (disbursement == null) {
            disbursement = disbursementRepository.findByApplication(application)
                    .orElseThrow(() -> new DisbursementException("Disbursement record not found."));
        }
        disbursement.setAmount(application.getScheme().getScholarshipAmount());
        disbursement.setDisbursementStatus(DisbursementStatus.DISBURSED);
        disbursement.setTransactionReference(disbursement.getTransactionReference() == null ? "ST-" + LocalDate.now().getYear() + "-" + String.format("%06d", (int) (Math.random() * 1000000)) : disbursement.getTransactionReference());
        disbursement.setDisbursementDate(LocalDate.now());
        disbursement.setRemarks("Payment successfully processed.");
        disbursementRepository.save(disbursement);

        applicationRepository.save(application);
        return mapToResponse(application);
    }

    private EligibilityStatus calculateEligibility(Student student, Scheme scheme) {
        boolean incomeMatch = student.getAnnualIncome() <= scheme.getIncomeLimit();
        boolean marksMatch = student.getMarksPercentage() >= scheme.getMinimumMarks();
        boolean categoryMatch = scheme.getEligibleCategories() == null || scheme.getEligibleCategories().isEmpty()
                || scheme.getEligibleCategories().stream().anyMatch(category -> category.equalsIgnoreCase(student.getCategory()));

        return (incomeMatch && marksMatch && categoryMatch) ? EligibilityStatus.ELIGIBLE : EligibilityStatus.INELIGIBLE;
    }

    private String generateApplicationNumber() {
        return "ST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private ApplicationResponseDto mapToResponse(Application application) {
        ApplicationResponseDto dto = new ApplicationResponseDto();
        dto.setId(application.getId());
        dto.setApplicationNumber(application.getApplicationNumber());
        dto.setStudentId(application.getStudent().getId());
        dto.setStudentName(application.getStudent().getStudentName());
        dto.setSchemeId(application.getScheme().getId());
        dto.setSchemeName(application.getScheme().getSchemeName());
        dto.setApplicationDate(application.getApplicationDate());
        dto.setEligibilityStatus(application.getEligibilityStatus());
        dto.setApplicationStatus(application.getApplicationStatus());
        dto.setSubmitted(application.isSubmitted());
        dto.setRemarks(application.getRemarks());
        return dto;
    }
}
