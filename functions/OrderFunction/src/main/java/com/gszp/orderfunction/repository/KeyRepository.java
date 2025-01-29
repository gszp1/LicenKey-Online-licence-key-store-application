package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key,Long> {
}
