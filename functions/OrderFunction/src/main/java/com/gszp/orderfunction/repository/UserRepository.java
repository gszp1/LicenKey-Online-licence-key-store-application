package com.gszp.orderfunction.repository;

import com.gszp.orderfunction.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"orders"})
    @Query("SELECT u FROM User u WHERE u.userId=:userId")
    User getByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"confirmedCartEntries"})
    @Query("SELECT u FROM User u WHERE u.userId=:userId")
    User getWithCartEntries(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"keys"})
    @Query("SELECT u FROM User u WHERE u.userId=:userId")
    User getWithKeys(@Param("userId") Long userId);


}
