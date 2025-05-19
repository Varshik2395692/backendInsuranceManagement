package com.cognizant.insurance.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.entity.UserPrincipal;
import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.exception.AllException.UserNotExist;
import com.cognizant.insurance.repository.UserRepo;


@Service
public class MyUserDetailsService implements UserDetailsService {
@Autowired
	UserRepo userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

Users user = userRepository.findByEmail(username).orElseThrow(() -> 
new UserNotExist("User with Email "+username+" didnt exist"));

return new UserPrincipal(user);
	}

}
