package com.marcincho.service_msg.repository;

import com.marcincho.service_msg.entity.UserEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEnt, UUID> {
    Optional<UserEnt> findByUsername(String username);

    List<UserEnt> findAllByUsernameNot(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
