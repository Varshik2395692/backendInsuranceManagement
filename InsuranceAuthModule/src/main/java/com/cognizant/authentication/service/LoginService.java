package com.cognizant.authentication.service;

import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cognizant.authentication.client.UserClient;
import com.cognizant.authentication.dto.AuthenticationRequestDTO;
import com.cognizant.authentication.dto.AuthenticationResponseDTO;
import com.cognizant.authentication.dto.AuthenticationResponseDTO;
import com.cognizant.authentication.util.JwtUtil;
import com.cognizant.authentication.dto.Role; // adjust the package as needed
import com.cognizant.authentication.dto.UserDTO;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
   

    @Autowired
    private UserClient userClient;

    

    public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request) {
        try {
        	UserDTO userDetails = userClient.getemail(request.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

            String roleString = userPrincipal.getAuthorities().stream().findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

            Role role = Role.valueOf(roleString); // Convert string to enum

            String token = jwtUtil.generateToken(userDetails.getEmail(), role.name());
            String name=userDetails.getName();
            return new AuthenticationResponseDTO(userDetails.getUserId(),name,userDetails.getEmail(),role,token);


        } catch (FeignException.NotFound ex) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (BadCredentialsException ex) {
            System.out.println("Invalid username or password");
            throw ex;
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        }
    }


}