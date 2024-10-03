package com.marcincho.service_msg.config;

import com.marcincho.service_msg.entity.UserEnt;
import com.marcincho.service_msg.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEnt userEnt = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User -> " + username + " not found"));

        Set<GrantedAuthority> authorities =
                userEnt.getRoleEnts().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());

        return UserDetailsImpl.builder()
                .username(userEnt.getUsername())
                .accountNotExpired(true)
                .accountNonLocked(true)
                .password(userEnt.getPassword())
                .authorities(authorities)
                .id(userEnt.getId())
                .build();
    }
}
