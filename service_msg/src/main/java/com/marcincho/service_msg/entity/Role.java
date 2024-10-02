package com.marcincho.service_msg.entity;

import com.marcincho.service_msg.models.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Table(name = "roles")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private ERole name;

}
