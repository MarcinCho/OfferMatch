package com.marcincho.service_msg.repository;

import com.marcincho.service_msg.entity.RoleEnt;
import com.marcincho.service_msg.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEnt, UUID> {

    Optional<RoleEnt> findByName(ERole name);

}
