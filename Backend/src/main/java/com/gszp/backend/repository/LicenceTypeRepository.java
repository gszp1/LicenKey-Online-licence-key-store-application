package com.gszp.backend.repository;

import com.gszp.backend.model.LicenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenceTypeRepository extends JpaRepository<LicenceType, Long> {
}
