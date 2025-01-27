package com.gszp.backend.repository;

import com.gszp.backend.model.ShoppingCart;
import com.gszp.backend.model.keys.ShoppingCartKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartKey> {

    @EntityGraph(attributePaths = {"licence", "user",})
    @Query("SELECT sp from ShoppingCart sp WHERE sp.user.email = :userEmail")
    List<ShoppingCart> getShoppingCartsByUserEmail(@Param("userEmail") String userEmail);
}
