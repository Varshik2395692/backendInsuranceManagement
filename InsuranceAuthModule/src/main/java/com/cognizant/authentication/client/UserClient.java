package com.cognizant.authentication.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognizant.authentication.dto.AuthenticationRequestDTO;
import com.cognizant.authentication.dto.AuthenticationResponseDTO;
import com.cognizant.authentication.dto.UserDTO;
@FeignClient(name = "Insurance-mgmt")
public interface UserClient {

@GetMapping("/users/{email}")
UserDTO getemail(@PathVariable("email") String email);
}

