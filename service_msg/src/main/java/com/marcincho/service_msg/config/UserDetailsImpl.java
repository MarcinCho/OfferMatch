package com.marcincho.service_msg.config;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@Builder
@Data
public class UserDetailsImpl implements UserDetails {

    private final UUID id;

    private String password;

    private final String username;
    private final String email;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNotExpired;

    private final boolean accountNonLocked;

}
