package com.cognizant.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.dto.AuthenticationRequestDTO;
import com.cognizant.authentication.dto.AuthenticationResponseDTO;
import com.cognizant.authentication.service.LoginService;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request) {
        try {
            AuthenticationResponseDTO response = authenticationService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } 
        catch (BadCredentialsException ex) {
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        	}catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + ex.getMessage());
        }
    }
}
