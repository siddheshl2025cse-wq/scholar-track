package com.scholartrack.repository;

import com.scholartrack.entity.Application;
import com.scholartrack.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {
    Optional<Disbursement> findByApplication(Application application);
}
