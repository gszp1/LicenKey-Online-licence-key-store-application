package com.gszp.backend.repository;

import com.gszp.backend.model.Order;
import com.gszp.backend.model.keys.OrdersKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, OrdersKey> {

    @EntityGraph(attributePaths = {"user", "licence"})
    @Query("SELECT o FROM Order o WHERE o.user.email=:userEmail")
    List<Order> getAllOrdersByUserEmail(String userEmail);
}
