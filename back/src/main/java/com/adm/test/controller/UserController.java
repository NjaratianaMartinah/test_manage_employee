package com.adm.test.controller;

import com.adm.test.dto.request.LoginDto;
import com.adm.test.dto.request.RegistrationDto;
import com.adm.test.dto.response.ApiResponse;
import com.adm.test.dto.response.LoginResponse;
import com.adm.test.service.AuthenticationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger log = LogManager.getLogger(UserController.class);
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegistrationDto registration) {
        log.info("Registering user with email: {}", registration.getEmail());
        HttpStatus status = HttpStatus.CREATED;
        String message = "User registered successfully";
        try {
            authenticationService.registerUser(registration);
            log.info("User registered successfully: {}", registration.getEmail());
        } catch (DataIntegrityViolationException e) {
            log.error("Registration failed for email: {} - {}", registration.getEmail(), e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            message = "Email or phone number for registration already used";
        }
        return ApiResponse.buildResponse(null, status, message);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginDto request) {
        log.info("Authenticating user with email: {}", request.getEmail());
        HttpStatus status = HttpStatus.OK;
        LoginResponse response = null;
        String message = "User authenticated successfully";
        try {
            response = authenticationService.authenticate(request);
            log.info("User authenticated successfully: {}", request.getEmail());
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {} - {}", request.getEmail(), e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            message = "Invalid email or password";
        }
        return ApiResponse.buildResponse(response, status, message);
    }
}
