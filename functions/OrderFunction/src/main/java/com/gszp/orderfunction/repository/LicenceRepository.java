package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.Licence;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {

    @EntityGraph(attributePaths = {"orders"})
    @Query("SELECT l FROM Licence l WHERE l.licenceId IN :idList")
    List<Licence> getLicencesByLicenceId(List<Long> idList);

    @EntityGraph(attributePaths = {"confirmedCartEntries"})
    @Query("SELECT l FROM Licence l WHERE l.licenceId IN :idList")
    List<Licence> getLicencesByLicenceIdWithCarts(List<Long> idList);

    @EntityGraph(attributePaths = {"keys"})
    @Query("SELECT l FROM Licence l WHERE l.licenceId IN :idList")
    List<Licence> getLicencesByLicenceIdWithKeys(List<Long> idList);

}
