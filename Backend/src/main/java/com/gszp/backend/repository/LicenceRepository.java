package com.gszp.backend.repository;

import com.gszp.backend.dto.response.LicenceDescriptionResponse;
import com.gszp.backend.model.Licence;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
    LicenceDescriptionResponse getLicenceByLicenceId(Long licenceId);

    @EntityGraph(attributePaths = {"platform", "category", "licenceType", "publisher"})
    @Query("SELECT l " +
            "from Licence l " +
            "WHERE :keyword IS NULL OR :keyword = '' OR " +
            "LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Licence> getLicencesByKeyword(String keyword);
}
