package org.github.gszp1.usersservice.repository;

import org.github.gszp1.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
