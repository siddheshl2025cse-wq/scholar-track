package com.scholartrack.repository;

import com.scholartrack.entity.Application;
import com.scholartrack.entity.Verification;
import com.scholartrack.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByApplication(Application application);
    List<Verification> findByVerificationStatus(VerificationStatus verificationStatus);
}
