package com.marcincho.service_msg.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.RoleEnt;
import com.marcincho.service_msg.entity.UserEnt;
import com.marcincho.service_msg.models.ERole;
import com.marcincho.service_msg.models.JwtResponse;
import com.marcincho.service_msg.models.LoginRequestDto;
import com.marcincho.service_msg.models.ResponseMessage;
import com.marcincho.service_msg.models.SignupRequest;
import com.marcincho.service_msg.repository.RoleRepository;
import com.marcincho.service_msg.repository.UserRepository;
import com.marcincho.service_msg.utils.JWTUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
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

        JwtResponse jwtResponse
                = JwtResponse.builder()
                        .token(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
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
        if (userRepository.existsByEmail(signupRequest.email())) {
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
                            case "ADMIN" -> {
                                RoleEnt admin = roleRepository.findByName(ERole.ADMIN)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(admin);
                            }
                            case "MODERATOR" -> {
                                RoleEnt moderator = roleRepository.findByName(ERole.MODERATOR)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(moderator);
                            }
                            case "COMPANY" -> {
                                RoleEnt company = roleRepository.findByName(ERole.COMPANY)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(company);
                            }
                            default -> {
                                RoleEnt userRole = roleRepository.findByName(ERole.USER)
                                        .orElseThrow(NoSuchElementException::new);
                                roles.add(userRole);
                            }
                        }
                    }
            );
        }
        user.setRoleEnts(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered!", new Date()));

    }

}
