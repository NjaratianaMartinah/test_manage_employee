package com.adm.test.service;

import com.adm.test.dto.request.LoginDto;
import com.adm.test.dto.request.RegistrationDto;
import com.adm.test.dto.response.LoginResponse;
import com.adm.test.entity.User;
import com.adm.test.utility.JwtUtil;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final static Logger log = LogManager.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse authenticate(LoginDto request) {
        log.info("Attempting authentication for email: {}", request.getEmail());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = (User) auth.getPrincipal();
        String token = jwtUtil.generateToken(request.getEmail(), null);
        log.info("Authentication successful for email: {}", request.getEmail());
        return LoginResponse.fromUser(user, token);
    }

    @Transactional
    public void registerUser(RegistrationDto registration) {
        log.info("Registering user with username: {}", registration.getEmail());
        var optionalUser = userService.findUserByName(registration.getEmail());
        if (optionalUser.isPresent()) {
            log.error("Registration failed - User already exists: {}", registration.getEmail());
            throw new DuplicateKeyException("User already exists");
        }
        User user = new User();
        user.setUsername(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        userService.saveUser(user);
        log.info("User registered successfully: {}", registration.getEmail());
    }
}
