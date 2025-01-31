package com.gszp.backend.repository;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.model.Key;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

    @EntityGraph(attributePaths = {"licence", "licence.publisher", "licence.platform"})
    @Query("SELECT k FROM Key k WHERE k.user.email=:userEmail")
    List<Key> findAllKeysByUserEmail(@Param("userEmail") String userEmail);

    String user(User user);
}
