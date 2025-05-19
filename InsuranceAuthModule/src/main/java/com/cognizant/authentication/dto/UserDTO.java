package com.cognizant.authentication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private int userId;
	private String name;
	private String email;
	private Role role;
	private String token;
	private String password;
 
 

}
