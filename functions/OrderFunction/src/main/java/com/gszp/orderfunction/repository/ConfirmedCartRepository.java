package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.ConfirmedCart;
import com.gszp.orderfunction.model.ConfirmedCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedCartRepository extends JpaRepository<ConfirmedCart, ConfirmedCartKey> {
}
