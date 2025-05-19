package com.cognizant.insurance.dto;



import com.cognizant.insurance.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

	@NotBlank
	@Size(min=4,max=50,message="Name must be 4 to 50 characters")
	//if we keep only @Size it will also work . but it allows '   ' as a name(empty or blank)
	//so we kept not blank on top
	private String name;
	@NotBlank
	@Size(min=4,message="Password should be atleast 4 characters long")
	private String password;
	
	@NotBlank
	@Email
//@Pattern(regexp="^[a-z][a-z0-9]*@gmail\\.com",message="invalid email")
	private String email;
@NotNull
	private Role role;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}

}
