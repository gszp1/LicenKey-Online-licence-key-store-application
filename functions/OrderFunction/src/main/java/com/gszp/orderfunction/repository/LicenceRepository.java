package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.Licence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
}
