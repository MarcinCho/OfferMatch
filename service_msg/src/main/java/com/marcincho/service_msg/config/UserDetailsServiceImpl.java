package com.marcincho.service_msg.config;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;


@AllArgsConstructor
@Builder
@Data
public class UserDetailsServiceImpl implements UserDetails {

    private final Long id;

    private String password;

    private final String username;
    private final String email;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNotExpired;

    private final boolean accountNonLocked;

}
