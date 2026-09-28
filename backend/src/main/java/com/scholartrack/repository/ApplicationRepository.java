package com.scholartrack.repository;

import com.scholartrack.entity.Application;
import com.scholartrack.enums.ApplicationStatus;
import com.scholartrack.enums.EligibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    Optional<Application> findByApplicationNumber(String applicationNumber);
    List<Application> findByApplicationStatus(ApplicationStatus status);
    List<Application> findByEligibilityStatus(EligibilityStatus status);
}
