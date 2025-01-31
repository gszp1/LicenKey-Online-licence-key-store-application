package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.ConfirmedCart;
import com.gszp.orderfunction.model.ConfirmedCartKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConfirmedCartRepository extends JpaRepository<ConfirmedCart, ConfirmedCartKey> {

    @EntityGraph(attributePaths = {"licence", "user" })
    @Query("SELECT c FROM ConfirmedCart c WHERE c.key.orderIdentifier=:orderIdentifier")
    List<ConfirmedCart> getConfirmedCartsByOrOrderIdentifier(UUID orderIdentifier);
}
