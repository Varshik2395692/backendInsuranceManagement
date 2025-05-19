package com.cognizant.insurance.dto;

import com.cognizant.insurance.entity.Role;

import lombok.Data;

@Data
public class RegisterResponseDTO {
	private int userId;
	private String name;
	private String email;
	private Role role;
}
