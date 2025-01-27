package com.gszp.backend.repository;

import com.gszp.backend.model.ConfirmedCart;
import com.gszp.backend.model.keys.ConfirmedCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedCartRepository extends JpaRepository<ConfirmedCart, ConfirmedCartKey> {
}
