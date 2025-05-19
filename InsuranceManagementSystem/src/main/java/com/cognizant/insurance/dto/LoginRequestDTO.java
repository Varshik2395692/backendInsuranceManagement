package com.cognizant.insurance.dto;

import com.cognizant.insurance.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

	@NotBlank
	@Email
private	String email;
@NotBlank
private	String password;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
private Role role;
public LoginRequestDTO(@NotBlank @Email String email, @NotBlank String password, Role role) {
	super();
	this.email = email;
	this.password = password;
	this.role = role;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}
	
}
