package com.marcincho.service_msg.controller;


import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.RoleEnt;
import com.marcincho.service_msg.entity.UserEnt;
import com.marcincho.service_msg.models.*;
import com.marcincho.service_msg.repository.RoleRepository;
import com.marcincho.service_msg.repository.UserRepository;
import com.marcincho.service_msg.utils.JWTUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JWTUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JwtResponse jwtResponse =
                JwtResponse.builder()
                        .token(jwt)
                        .id(userDetails.getId())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build();

        response.addCookie(new Cookie("access_token", jwt));
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userSignIn(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.username())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Username is already taken.", new Date()));
        }
        if (userRepository.existsByEmail(signupRequest.email())){
            return ResponseEntity.badRequest().body(new ResponseMessage("Email is already in use.", new Date()));
        }

        UserEnt user = UserEnt.builder()
                .id(UUID.randomUUID())
                .username(signupRequest.username())
                .email(signupRequest.email())
                .password(encoder.encode(signupRequest.password()))
                .build();

        Set<String> reqRoles = signupRequest.role();
        Set<RoleEnt> roles = new HashSet<>();

        if (reqRoles == null) {
            RoleEnt userRole = roleRepository.findByName(ERole.USER).orElseThrow(NoSuchElementException::new);
            roles.add(userRole);
        } else {
            reqRoles.forEach(
                    role -> {
                        switch (role) {
                            case "ADMIN":
                                RoleEnt admin = roleRepository.findByName(ERole.ADMIN)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(admin);
                                break;
                            case "MODERATOR":
                                RoleEnt moderator = roleRepository.findByName(ERole.MODERATOR)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(moderator);
                                break;
                            case "COMPANY":
                                RoleEnt company = roleRepository.findByName(ERole.COMPANY)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(company);
                                break;
                            default:
                                RoleEnt userRole = roleRepository.findByName(ERole.USER)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(userRole);
                                break;
                        }
                    }
            );
        }
        user.setRoleEnts(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered!", new Date()));

    }

}

