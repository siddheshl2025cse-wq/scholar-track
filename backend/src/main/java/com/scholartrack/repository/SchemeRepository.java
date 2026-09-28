package com.scholartrack.repository;

import com.scholartrack.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
}
