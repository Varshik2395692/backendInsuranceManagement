package com.cognizant.authentication.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.util.JwtUtil;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class ValidationController {
	
	@Autowired
	private JwtUtil jwtutil;
 @GetMapping("/validateToken")
	 boolean validateToken(@RequestParam("token") String token) {
	 return jwtutil.validateToken(token);
 }

@GetMapping("/extractUsername")
	    String extractUsername(@RequestParam("token") String token) {
	return jwtutil.extractUsername(token);
}

	    @GetMapping("/extractRole")
	    String extractRole(@RequestParam("token") String token) {
	    	return jwtutil.extractRole(token);
	    }
}
