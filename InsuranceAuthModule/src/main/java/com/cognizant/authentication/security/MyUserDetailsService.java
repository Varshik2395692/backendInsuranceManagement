package com.cognizant.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.authentication.client.UserClient;
import com.cognizant.authentication.dto.UserDTO;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	UserDTO authenticationRequestDTO = userClient.getemail(email);
      if (authenticationRequestDTO == null) {
            throw new UsernameNotFoundException("User not found");
        }

String roleName = authenticationRequestDTO.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return new User(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword(), Collections.singletonList(authority));
    }
}




