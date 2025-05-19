package com.cognizant.insurance.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;


import com.cognizant.insurance.dto.LoginRequestDTO;
import com.cognizant.insurance.dto.LoginResponseDTO;
import com.cognizant.insurance.dto.RegisterRequestDTO;
import com.cognizant.insurance.dto.RegisterResponseDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.repository.UserRepo;
import com.cognizant.insurance.security.UserService;


import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    UserRepo userRepository;
    
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        ResponseEntity<RegisterResponseDTO> response = new ResponseEntity<>(userService.register(registerRequestDTO), HttpStatus.OK);
        
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
//        ResponseEntity<LoginResponseDTO> response = new ResponseEntity<>(userService.login(loginRequestDTO), HttpStatus.OK);
//        return response;
//    }
    @PostMapping("/validate-login")
    public UserDTO validateLogin(@RequestBody LoginRequestDTO request) {
    	UserDTO response=null;
    	try
    	{
    	System.out.println("user controller...........");
        response = userService.validateLogin(request);
        System.out.println("response....."+response);
        
    	}
    	catch(Exception e)
    	{
    		System.out.println("Invalid user");
    	}
    	return response;
    }
    @GetMapping("/users/{email}")
    public LoginResponseDTO getemail(@PathVariable("email") String email) {
    	return userService.getemail(email);
    }
}
