package com.marcincho.service_msg.repository;

import com.marcincho.service_msg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameNot(String username);
}
