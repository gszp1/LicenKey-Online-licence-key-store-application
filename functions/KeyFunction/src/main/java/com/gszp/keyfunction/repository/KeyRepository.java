package com.gszp.keyfunction.repository;


import com.gszp.keyfunction.model.Key;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

    @EntityGraph(attributePaths = {"licence", "licence.service"})
    @Query("SELECT k FROM Key k WHERE k.keyId=:id")
    Optional<Key> findById(Long id);
}
