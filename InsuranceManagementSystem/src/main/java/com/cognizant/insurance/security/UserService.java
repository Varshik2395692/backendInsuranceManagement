package com.cognizant.insurance.security;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognizant.insurance.dto.LoginRequestDTO;
import com.cognizant.insurance.dto.LoginResponseDTO;
import com.cognizant.insurance.dto.RegisterRequestDTO;
import com.cognizant.insurance.dto.RegisterResponseDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.exception.AllException.UserNotExist;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.CustomerRepository;
import com.cognizant.insurance.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	AgentRepository agentRepository;
	@Autowired
	UserRepo userRepository;

	@Autowired
	CustomerRepository customerRepository;

//	@Autowired
//	private JWTService jwtService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
		Users user = modelMapper.map(registerRequestDTO, Users.class);
		user.setPassword(encoder.encode(user.getPassword()));
		Users saveduser = null;
		if (user.getRole().name().equals("ROLE_AGENT")) {
			saveduser = agentRepository.save(modelMapper.map(user, Agent.class));
		} else if (user.getRole().name().equals("ROLE_CUSTOMER")) {
			saveduser = customerRepository.save(modelMapper.map(user, Customer.class));
		} else {
			// throw some exception later
			throw new UserNotExist("User with Role " + user.getRole().name() + " didnt exist");
		}

		return modelMapper.map(saveduser, RegisterResponseDTO.class);

	}

public UserDTO validateLogin(LoginRequestDTO request) {
	// TODO Auto-generated method stub
	Users user=null;
	Optional<Users> optional=userRepository.findByEmail(request.getEmail());
	if(optional.isPresent())
		user=optional.get();
System.out.println("In service ..."+user);
if (user == null)throw new UsernameNotFoundException("Invalid email or password");


	return modelMapper.map(user, UserDTO.class);
}

//public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
//	Users user=userRepository.findByEmail(loginRequestDTO.getEmail())
//			.orElseThrow(()->new  UserNotExist("User with Email "+loginRequestDTO.getEmail()+" didnt exist"));
//	LoginResponseDTO loginResponseDTO=modelMapper.map(user, LoginResponseDTO .class);
//	Authentication authentication=authenticationManager.authenticate(
//			new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));
//	if(authentication.isAuthenticated()) {
//	loginResponseDTO.setToken( jwtService.generateToken(user));
//	}
//	return loginResponseDTO;
	

	

public LoginResponseDTO getemail( String email) {
	Users user=userRepository.findByEmail(email).orElseThrow();
	LoginResponseDTO loginResponseDTO = modelMapper.map(user,LoginResponseDTO.class);
	return loginResponseDTO;
}

}
